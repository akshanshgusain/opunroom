package com.factor8.opUndoor.UI.Main.Feed

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Main.Feed.state.FeedViewState
import com.factor8.opUndoor.Util.Constants
import com.factor8.opUndoor.Util.Constants.Companion.FIREBASE_VIDEO_URL
import com.factor8.opUndoor.Util.Constants.Companion.STORY_IMAGES
import com.factor8.opUndoor.Util.Constants.Companion.STORY_TYPE_COMPANY_STORY
import com.factor8.opUndoor.Util.Constants.Companion.STORY_TYPE_FRIEND_STORY
import com.factor8.opUndoor.Util.Constants.Companion.STORY_TYPE_GROUP_STORY
import com.factor8.opUndoor.Util.Constants.Companion.limit
import com.factor8.opUndoor.Util.Constants.Companion.pressTime
import com.factor8.opUndoor.ViewStories.StoryViewerActivity
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_view_story.*

class ViewStoryFragment : BaseFeedFragment(), StoriesListener {
    private var counter = 0
    private  var listType = 0
    private lateinit var storyList: FeedViewState.StoryList

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = OnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                stories.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                stories.resume()
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reverse.setOnClickListener {
            stories.reverse()
        }
        reverse.setOnTouchListener(onTouchListener)

        skip.setOnClickListener {
            stories.skip()
        }
        skip.setOnTouchListener(onTouchListener)

        subscriberObservers()
    }

    override fun onDestroy() {
        //stories.destroy()
        super.onDestroy()
    }

    private fun subscriberObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {
                viewState.storyList?.let {
                    storyList = it
                    listType = it.listType
                    setImage()
                }
            }
        })
    }

    override fun onNext() {
        ++counter
        setImage()
    }

    override fun onPrev() {
        --counter
        setImage()
    }

    override fun onComplete() {
        findNavController().navigateUp()
    }

    private fun setImage() {
        when (listType) {
            STORY_TYPE_FRIEND_STORY -> {
                storyList.friendStory?.let { it ->
                    textView_full_name.text =
                        StringBuilder(it.feedFriendStore.f_name + " " + it.feedFriendStore.l_name)
                    textView_time.text = it.feedFriendStore.username
                    requestManager.load(Constants.PROFILE_IMAGES + it.feedFriendStore.profile_picture).into(imageView_dp)
                    constraintLayout_user_details.visibility = View.VISIBLE

                    stories.setStoriesCount(storyList.friendStory!!.storyPictures.size)
                    stories.setStoryDuration(5000L)
                    stories.setStoriesListener(this)
                    stories.startStories()

                    loadMediaIntoPlayer(it.storyPictures[counter].story_picture)
                }
            }
            STORY_TYPE_GROUP_STORY -> {

            }
            STORY_TYPE_COMPANY_STORY -> {

            }
            else -> {

            }
        }
    }

    private fun loadMediaIntoPlayer(story: String){
        //If the story is a story
        if (!story.contains(FIREBASE_VIDEO_URL)) {
            switchPlayer(1)
            pauseStory()
            progressBar_load.visibility = View.VISIBLE

            requestManager
                .load(STORY_IMAGES+story)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar_load.visibility = View.GONE
                        resumeStory()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar_load.visibility = View.GONE
                        resumeStory()
                        return false
                    }
                })
                .into(imageView_story)
        }
        else{
            switchPlayer(1)
            pauseStory()
            progressBar_load.visibility = View.VISIBLE

            videoView_story.setVideoPath(story)
            videoView_story.setOnPreparedListener {
                progressBar_load.visibility = View.GONE
                onResume()
            }
            videoView_story.start()
            videoView_story.setOnCompletionListener {
                stories.skip()
            }

        }
    }

    private fun switchPlayer(player: Int){
        //1 - Image
        //2 - Video
        if (player == 1) {
            imageView_story.visibility = View.VISIBLE
            videoView_story.visibility = View.GONE
        } else {
            imageView_story.visibility = View.GONE
            videoView_story.visibility = View.VISIBLE
        }
    }

    private fun pauseStory() {
        val mainHandler: Handler = Handler(requireActivity().mainLooper)
        val myRunnable = Runnable { stories.pause() }
        mainHandler.post(myRunnable)
    }
    private fun resumeStory() {
        val mainHandler: Handler = Handler(requireActivity().mainLooper)
        val myRunnable = Runnable { stories.resume() }
        mainHandler.post(myRunnable)
    }
}