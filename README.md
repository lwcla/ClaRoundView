# ClaRoundView

用代码去控制view的边框以及背景色


### 使用方式
```java
 implementation 'io.github.lwcla:cla_round_view:1.0.0'
```

### 属性说明
```xml
 <!--是否在按压状态改变按钮的透明度-->
 <attr name="cla_view_changeAlpha_whenPress" format="boolean" />
 <!--是否在disable状态改变按钮的透明度-->
 <attr name="cla_view_changeAlpha_whenDisable" format="boolean" />
 <!--按压状态按钮的透明度 0f-1f-->
 <attr name="cla_view_pressAlpha" format="float" />
 <!--disable状态按钮的透明度 0f-1f-->
 <attr name="cla_view_disableAlpha" format="float" />

 <!--在view的上下左右分别画一条线-->
 <!--线条的粗度-->
 <attr name="cla_view_lineWidth" format="dimension" />
 <!--虚线的长度-->
 <attr name="cla_view_lineDashWidth" format="dimension" />
 <!--虚线的间隔-->
 <attr name="cla_view_lineDashGap" format="dimension" />
 <!--线条颜色-->
 <attr name="cla_view_lineColor" format="color" />
 <!--线条颜色透明度-->
 <attr name="cla_view_lineColorAlpha" format="float" />
 <!--线条与两边的空间-->
 <attr name="cla_view_lineSpace" format="dimension" />
 <!--线条与顶部的空间-->
 <attr name="cla_view_lineTopSpace" format="dimension" />
 <!--线条与底部的空间-->
 <attr name="cla_view_lineBottomSpace" format="dimension" />
 <!--线条与左边的空间-->
 <attr name="cla_view_lineLeftSpace" format="dimension" />
 <!--线条与右边的空间-->
 <attr name="cla_view_lineRightSpace" format="dimension" />
 <!--是否显示view顶部的线条-->
 <attr name="cla_view_showTopLine" format="boolean" />
 <!--是否需要左边的线条-->
 <attr name="cla_view_showLeftLine" format="boolean" />
 <!--是否需要右边的线条-->
 <attr name="cla_view_showRightLine" format="boolean" />
 <!--是否需要下边的线条-->
 <attr name="cla_view_showBottomLine" format="boolean" />

 <!--普通状态-->
 <!--背景色-->
 <attr name="cla_normal_backgroundColor" format="color" />
 <!--背景色的透明度 0-1-->
 <attr name="cla_normal_backgroundColorAlpha" format="float" />
 <!--边框的宽度-->
 <attr name="cla_normal_borderWidth" format="dimension" />
 <!--边框虚线的宽度，设置为0时就不显示虚线-->
 <attr name="cla_normal_borderDashWidth" format="dimension" />
 <!--边框虚线的距离，不设置时等于borderDashWidth-->
 <attr name="cla_normal_borderDashGap" format="dimension" />
 <!--边框的颜色-->
 <attr name="cla_normal_borderColor" format="color" />
 <!--边框颜色的透明度 0-1-->
 <attr name="cla_normal_borderColorAlpha" format="float" />
 <!--设置圆角大小是否自动适应为 View 的高度的一半-->
 <attr name="cla_normal_radiusAdjustBounds" format="boolean" />
 <!--边框的左上圆角-->
 <attr name="cla_normal_topLeftRadius" format="dimension" />
 <!--边框的右上圆角-->
 <attr name="cla_normal_topRightRadius" format="dimension" />
 <!--边框的左下圆角-->
 <attr name="cla_normal_bottomLeftRadius" format="dimension" />
 <!--边框的右下圆角-->
 <attr name="cla_normal_bottomRightRadius" format="dimension" />
 <!--边框的圆角-->
 <attr name="cla_normal_radius" format="dimension" />
 <!--设置背景图片，这个优先级最高，有图片的情况下，背景色和边框都不会生效-->
 <attr name="cla_normal_drawable" format="reference" />
 <!--文字的颜色-->
 <attr name="cla_normal_textColor" format="color" />
 <!--文字颜色的透明读 0-1-->
 <attr name="cla_normal_textColorAlpha" format="float" />
 <!--普通状态-->
 
 <!--按压状态-->
 <attr name="cla_press_backgroundColor" format="color" />
 ...其他属性同普通状态...
 <!--按压状态-->

 <!--选中状态-->
 <attr name="cla_select_backgroundColor" format="color" />
 ...其他属性同普通状态...
 <!--选中状态-->

 <!--获取焦点状态-->
 <attr name="cla_focus_backgroundColor" format="color" />
 ...其他属性同普通状态...
 <!--获取焦点状态-->

 <!--activated状态-->
 <attr name="cla_activated_backgroundColor" format="color" />
 ...其他属性同普通状态...
 <!--activated状态-->

 <!--disable状态-->
 <attr name="cla_disable_backgroundColor" format="color" />
 ...其他属性同普通状态...
 <!--disable状态-->

```

### 参考
-------
QmUI</br>
https://github.com/Tencent/QMUI_Android</br>
BackgroundLibrary</br>
https://github.com/JavaNoober/BackgroundLibrary</br>
