package com.factor8.opUndoor.DI.Main

import com.factor8.opUndoor.UI.Auth.LoginFragment
import com.factor8.opUndoor.UI.Main.Account.AccountFragment
import com.factor8.opUndoor.UI.Main.Account.AccountSettingsFragment
import com.factor8.opUndoor.UI.Main.Camera.CameraFragment
import com.factor8.opUndoor.UI.Main.Chat.ChatFragment
import com.factor8.opUndoor.UI.Main.Feed.CreateGroupFragment
import com.factor8.opUndoor.UI.Main.Feed.FeedFragment
import com.factor8.opUndoor.UI.Main.Feed.ViewStoryFragment
import com.factor8.opUndoor.UI.Main.ViewPagerContainer
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule{

    @ContributesAndroidInjector()
    abstract fun contributeChatFragment(): ChatFragment

    @ContributesAndroidInjector()
    abstract fun contributeCameraFragment(): CameraFragment

    @ContributesAndroidInjector()
    abstract fun contributeFeedFragment(): FeedFragment

    @ContributesAndroidInjector()
    abstract fun contributeViewPagerContainerFragment(): ViewPagerContainer

    @ContributesAndroidInjector()
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector()
    abstract fun contributeAccountSettingsFragment(): AccountSettingsFragment

    @ContributesAndroidInjector()
    abstract fun contributeViewStoryFragment(): ViewStoryFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateGroupFragment(): CreateGroupFragment
    }
