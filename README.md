# android-common-kit

* **common** : 一些工具类和 `kotlin extensions`
* **kotlinBridge** : 参考文章 [用Kotlin实现极简回调](https://juejin.im/post/6844903760121036807)
* **mvp** : mvp架构的封装实现
* **models** : `mvvm`模式中为数据赋予状态信息的包装实现，`View` 层获取到数据或根据数据的 `status` 判断显示 `loading、error、succeeded` 状态 。项目中的 `WorkResultDialogObserver`和 ·、`WorkResultObserver` 是其二次封装
* **listing** : 一套对于 `list` 列表 `initialized` 、 `loadMore`、  `error retry` 、`refresh` 在 `mvvm` 模式下的 list 状态管理的 model 层封装实现.
