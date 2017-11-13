# MyLineView

![](https://github.com/qiaojiuyuan/MyLineView/raw/master/img/line_view.png)
```
最近看了HenCoder的自定义View部分，想着做出来个东西练练手，正好项目中这期版本有个界面要用到这方面的
知识，然后就做出来了。
期间碰到了一个比较不好查的问题，发现获取字体宽度方面有时能获取正确，有时获取的值不对，而且是随机出现
的，当时以为是坐标计算错了，但怎么算也发现没有错，后来打Log发现字的宽度在给paint设置字体size时居然
获取的值不一样，然后把获取字宽度的方法在paint字体大小方面的后面就正确了。算是踩了一个坑吧。代码里有
注释。
```
