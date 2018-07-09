
[![](https://jitpack.io/v/lovestack/YYPhoto.svg)](https://jitpack.io/#lovestack/YYPhoto)

# YYPhoto
仿微信图片浏览

### 方式一:Gradle添加：
   #### 1.在Project的build.gradle中添加仓库地址

   ``` gradle
   	allprojects {
   		repositories {
   			...
   			maven { url "https://jitpack.io" }
   		}
   	}
   ```

   #### 2.在app目录下的build.gradle中添加依赖
   ``` gradle
   	dependencies {
   	    implementation 'com.github.lovestack:YYPhoto:version'
   	}
   ```

### 方式二:(方便自定义修改)下载源码使用Module添加：imagebrowserlibrary

``` gradle
	compile project(':yyphotoview')

```