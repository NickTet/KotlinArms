package com.ppjun.android.arms.base.delegate

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ppjun.android.arms.di.component.AppComponent
import com.ppjun.android.arms.integration.cache.Cache

/**
 * Created by ppjun on 3/6/18.
 */
interface IFragment {


    /**
     *
     * 提供在 {@link Fragment} 生命周期内的缓存容器, 可向此 {@link Fragment} 存取一些必要的数据
     * 此缓存容器和 {@link Fragment} 的生命周期绑定, 如果 {@link Fragment} 在屏幕旋转或者配置更改的情况下
     * 重新创建, 那此缓存容器中的数据也会被清空, 如果你想避免此种情况请使用 <a href="https://github.com/JessYanCoding/LifecycleModel">LifecycleModel</a>
     *
     *
     * @return like {@link LruCache}
     */
    @NonNull
    fun provideCache(): Cache<String, Any>

    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent
     */
    fun setupFragmentComponent(@NonNull appComponent: AppComponent)

    /**
     * 是否使用 {@link EventBus}
     *
     * @return
     */
    fun useEvebtBus(): Boolean

    /**
     * 初始化 View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    fun initView(@NonNull infalte: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle)

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    fun initData(@Nullable savedInstaceState: Bundle)

    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    fun setData(@Nullable data: Any)
}
