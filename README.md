# ClaRoundView

不用写xml，控制view的边框以及背景色</br>
参考了QmUi和BackgroundLibrary的实现方式</br>
其实就是根据属性去动态生成drawable，然后设置成view的背景图</br>
方案定下之后就是一个体力劳动 0.0</br>
</br>
qmui 感觉太重了，为了这个功能引入整个库，划不来。而且之前用qmui的TitleBar,坑好多，还是自己写的能控制得住</br>
BackgroundLibrar 看了他的代码之后，感觉还是有些复杂，我希望能简单一点
</br>

### 效果
<img src="https://github.com/lwcla/ClaRoundView/blob/main/img/SM-G9500_20220209173148.gif" width=270 height=555> 

### demo
<img src="https://github.com/lwcla/ClaRoundView/blob/main/img/QQ%E6%88%AA%E5%9B%BE20220708180245.png" width=180 height=180>

### 使用方式
```java
 implementation 'io.github.lwcla:cla_round_view:1.0.14'
```

### 属性说明
```xml
 <!--是否在按压状态改变按钮的透明度 默认为false-->
 <attr name="cla_view_changeAlpha_whenPress" format="boolean" />
 <!--是否在disable状态改变按钮的透明度 默认为false-->
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
 <!--线条颜色透明度 0f-1f-->
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
 <!--背景色的透明度 0f-1f-->
 <attr name="cla_normal_backgroundColorAlpha" format="float" />
 <!--边框的宽度-->
 <attr name="cla_normal_borderWidth" format="dimension" />
 <!--边框虚线的宽度，设置为0时就不显示虚线-->
 <attr name="cla_normal_borderDashWidth" format="dimension" />
 <!--边框虚线的距离，不设置时等于borderDashWidth-->
 <attr name="cla_normal_borderDashGap" format="dimension" />
 <!--边框的颜色-->
 <attr name="cla_normal_borderColor" format="color" />
 <!--边框颜色的透明度 0f-1f-->
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
 <!--文字颜色的透明度 0f-1f-->
 <attr name="cla_normal_textColorAlpha" format="float" />
 <!--文字style-->
 <attr name="cla_normal_textStyle" format="flags">
     <flag name="normal" value="0" />
     <flag name="bold" value="1" />
     <flag name="italic" value="2" />
 </attr>
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

### xml中设置样式
```xml
//设置各种状态
 <cn.cla.round.view.widget.ClaRoundTextView
      ...
      android:text="这是测试"
      app:cla_activated_backgroundColor="@color/transparent"
      app:cla_activated_textColor="@color/black"
      app:cla_activated_textColorAlpha="0.5"
      app:cla_disable_backgroundColor="@color/c5"
      app:cla_disable_backgroundColorAlpha="0.5"
      app:cla_disable_borderColor="@color/black"
      app:cla_disable_borderColorAlpha="0.6"
      app:cla_disable_borderWidth="2dp"
      app:cla_disable_radius="15dp"
      app:cla_disable_textColor="@color/white"
      app:cla_focus_backgroundColor="@color/black"
      app:cla_focus_borderColor="@color/c1"
      app:cla_focus_borderColorAlpha="0.7"
      app:cla_focus_borderWidth="4dp"
      app:cla_focus_radiusAdjustBounds="true"
      app:cla_focus_textColor="@color/white"
      app:cla_focus_textColorAlpha="0.7"
      app:cla_normal_backgroundColor="@color/c1"
      app:cla_normal_backgroundColorAlpha="0.1"
      app:cla_normal_borderColor="@color/c3"
      app:cla_normal_borderColorAlpha="0.3"
      app:cla_normal_borderDashGap="10dp"
      app:cla_normal_borderDashWidth="5dp"
      app:cla_normal_borderWidth="2dp"
      app:cla_normal_bottomLeftRadius="10dp"
      app:cla_normal_bottomRightRadius="20dp"
      app:cla_normal_radiusAdjustBounds="true"
      app:cla_normal_textColor="@color/black"
      app:cla_normal_textColorAlpha="0.5"
      app:cla_normal_topLeftRadius="20dp"
      app:cla_normal_topRightRadius="10dp"
      app:cla_press_backgroundColor="@color/c2"
      app:cla_press_backgroundColorAlpha="0.1"
      app:cla_press_borderColor="@color/black"
      app:cla_press_borderColorAlpha="0.1"
      app:cla_press_borderWidth="4dp"
      app:cla_press_radiusAdjustBounds="true"
      app:cla_press_textColor="@color/black"
      app:cla_press_textColorAlpha="0.3"
      app:cla_select_backgroundColor="@color/colorPrimaryDark"
      app:cla_select_backgroundColorAlpha="0.1"
      app:cla_select_borderColor="@color/colorPrimaryDark"
      app:cla_select_borderColorAlpha="0.3"
      app:cla_select_borderWidth="1dp"
      app:cla_select_bottomLeftRadius="20dp"
      app:cla_select_bottomRightRadius="10dp"
      app:cla_select_textColor="@color/white"
      app:cla_select_topLeftRadius="10dp"
      app:cla_select_topRightRadius="20dp" />

//设置上下的线条
 <cn.cla.round.view.widget.ClaRoundConstraintLayout
     ...
     app:cla_view_lineColor="@color/black"
     app:cla_view_lineColorAlpha="0.1"
     app:cla_view_lineWidth="2dp"
     app:cla_view_showBottomLine="true"
     app:cla_view_showTopLine="true">
 
     ...
  
 </cn.cla.round.view.widget.ClaRoundConstraintLayout>

//设置按压时透明效果
<cn.cla.round.view.widget.ClaRoundImageView
     ...
     android:src="@mipmap/ic_launcher_round"
     app:cla_view_changeAlpha_whenPress="true"
     app:cla_view_pressAlpha="0.1" />
```

### 代码中设置样式

```java
//可以设置的样式
enum class ClaRoundStateType {
    NORMAL, //普通状态
    PRESSED,//按压状态
    SELECTED,//选中状态
    FOCUS,//获取焦点状态
    ACTIVATED,//激活状态
    DISABLE, //disable状态
}
````

```java
//设置按压时的背景以及文字颜色
 tvRound.setClaBackground(ClaRoundStateType.PRESSED) {
     //按压时显示为渐变色
     //这里为了考虑灵活性，没有在xml中定义属性，如果要设置渐变色，那么就只能在代码中设置
     val gd = GradientDrawable()
     gd.shape = GradientDrawable.RECTANGLE
     gd.cornerRadius = 12.dpf
     gd.setStroke(4, Color.RED) //设置宽度为10px的红色描边
     //设置线性渐变，除此之外还有：GradientDrawable.SWEEP_GRADIENT（扫描式渐变），GradientDrawable.RADIAL_GRADIENT（圆形渐变）
     gd.gradientType = GradientDrawable.LINEAR_GRADIENT
     //增加渐变效果需要使用setColors方法来设置颜色（中间可以增加多个颜色值）
     gd.colors = intArrayOf(Color.RED, Color.BLUE, Color.BLACK, Color.WHITE, Color.YELLOW)
     drawable = gd

     //按压时修改文字颜色
     textColor = colorValue(R.color.white)
     textColorAlpha = 1f
 }
```

```java
//连续修改同一个状态的背景色
btnChangeActivated.setOnClickListener {
     thread {
         runOnUiThread {
             tvRound.setClaBackground(ClaRoundStateType.ACTIVATED) {
                 //设置边框
                 borderColor = colorValue(R.color.c1)
                 borderColorAlpha = 0.5f
                 borderWidth = 4.dp.toFloat()
                 radius = 15.dp.toFloat()
                 //设置背景色
                 bgColor = colorValue(R.color.c1)
                 bgColorAlpha = 0.2f
                 //设置文字颜色
                 textColor = colorValue(R.color.c1)
                 textColorAlpha = 0.5f
             }
         }

         Thread.sleep(2000)

         runOnUiThread {
             tvRound.setClaBackground(ClaRoundStateType.ACTIVATED) {
                 //设置成一张图片
                 drawable = drawableValue(R.mipmap.ic_launcher)
             }
         }

         Thread.sleep(2000)

         runOnUiThread {
             tvRound.setClaBackground(ClaRoundStateType.ACTIVATED) {
                 //设置渐变色
                 val gd = GradientDrawable()
                 gd.shape = GradientDrawable.RECTANGLE
                 gd.cornerRadius = 12.dp.toFloat()
                 gd.setStroke(4, Color.RED) //设置宽度为10px的红色描边
                 //设置线性渐变，除此之外还有：GradientDrawable.SWEEP_GRADIENT（扫描式渐变），GradientDrawable.RADIAL_GRADIENT（圆形渐变）
                 gd.gradientType = GradientDrawable.LINEAR_GRADIENT
                 //增加渐变效果需要使用setColors方法来设置颜色（中间可以增加多个颜色值）
                 gd.colors = intArrayOf(Color.RED, Color.BLUE, Color.BLACK, Color.WHITE, Color.YELLOW)
                 drawable = gd
                 
                 //设置文字颜色
                 textColor = colorValue(R.color.white)
                 textColorAlpha = 1f
             }
         }
     }
 }
```

```java
//代码设置view上下左右的线条
 tvChangeLine.setOnClickListener {
     tvBottomLine.resetClaLine {
         //虚线长度设置为0，也就是虚线改成实线
         lineDashWidth = 0f
         //把线条左边的间距改为2dp
         lineLeftSpace = 2.dp.toFloat()
         //修改线条的颜色
         lineColor = colorValue(R.color.c3)
     }
 }
```

### 参考
-------
QmUI</br>
https://github.com/Tencent/QMUI_Android</br>
BackgroundLibrary</br>
https://github.com/JavaNoober/BackgroundLibrary</br>
