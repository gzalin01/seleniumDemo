package com.seleniumTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
selenium常见API
  打开地址
  Driver.get()
  Driver.navigate.to()

  操作浏览器
  Driver.navigate.refresh() 刷新
  Driver.navigate.forward() 前进
  Driver.navigate.back()    后退
  Driver.getTitle()         页面title
  Driver.getCurrentUrl()    获取当前url
  Driver.manage().window().maximize()   窗口最大化
  Driver.quite()            不完全关闭还占用进程资源
  Driver.close()            完全关闭不占用进程资源

  滚动条操作   //须将driver转为js对象
  JavascriptExecutor js=(JavascriptExecutor)driver
  js.executeScript("window.scrollTo(0,document.body.scrollHeigth)")

  富文本框操作(注意先定位到作用域)
  JavascriptExecutor js=(JavascriptExecutor)driver
  js.executeScript("window.scrollTo(0,document.getElementById("#id").contentWindow.body.innerHTML="sss")")

  浏览器cookie操作
  getcookie();
  driver.manage().deleteAllCookies()
  driver.manage().addCookie(cookie)

  输入框操作
  Sendkeys()
  Clear()
  getText()
  Click()

  选择框处理
  单选框
  WebElement radio=driver.findElement(By.name("sex"));
  message("检查元素是否选中api进行中")；
  Checkbox(radio,"单选框")；

  复选框
  WebElement checkBox=driver.findElement(By.name("Bike"));
  Checkbox(checkBox,"复选框")；

  下拉框
  Select list=new Select(driver.findElement(By.name("listName")));
  list.selectByValue("下拉框三")

  页面元素处理
     等待加载/超时
         线程休眠：Thread.sleep(3000)//（单位毫秒）
         隐式超时driver.manage().timeouts().implicitlyWait(second, TimeUnit.SECONDS)//单位秒
         显示超时WebDriverWait wait = new WebDriverWait(driver, timeOut);  // timeOut为等待时间，单位秒
     根据属性获取元素值
     获取对象的css属性
     获取对象状态
     public void Checkbox(WebElement box,String log){
        if(!box.isEnable()){
            message1("检查元素是否可用")
        }
        if(!box.isSelected()){
            message1("检查元素是否被选中")
            box.click();
        }
        if(!box.isDisplay()){
            message1("检查元素是否可见")
        }
     }

  窗口处理
      窗口最大化driver.manage().window().maximize()
      打开窗口driver.get(url);
      关闭窗口driver.quite();driver.close();
  js操作
    js定位
    js单击元素


  特殊API
    报错时截屏保存
    模拟鼠标右键操作
    上传文件
    日期控件处理
    浏览器滚动条处理


  检查元素是否存在
        在进行下一步操作的时候检查页面的元素是否存在，对于构建一个稳定的测试是很有帮助的。
        private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        return false;
            }
         }

  通过网页内容识别和处理一个弹出窗口
       getPageSource().contains("")——这种方法速度比较慢。

  处理一个简单的JavaScript警告框
        WebDriver提供了Alert类来处理JavaScript警告框。
        Alert alertBox = driver.switchTo().alert();
        alertBox.accept();
        alertBox.getText()得到警告框上的信息。

  处理一个确认框（选择OK或Cancel按钮）
        alertBox.accept();//点击确认按钮
        alertBox.dismiss();//点击取消按钮

  处理一个提示框
    （除了确认和取消按钮之外，还可以用来让用户进行数据输入操作）
    Alert promptAlert = driver.switchTo().alert();
    //输入一些数据
    promptAlert.sendKeys("selenium提示框");
    //点击确定按钮
    promptAlert.accept();

  识别处理框架（frame）
        ---可通过ID或name识别框架
        Driver.switchTo().frame(“id or name”);//通过ID或name定位到frame
        Driver.switchTo().defaultContent();//进行了一次跳转后需要先定位到默认框架中，在跳转到其他框架
        ---还可以通过索引index来获取焦点

    通过页面内容识别和处理框架
        Driver.switchTo().frame();
        Driver.getPageSource().contains(“middle”);

    处理iframe
        如果iframe存在父框架，需要先找到父框架，再定位到iframe。
        如：
        //首先获得父窗口
        driver.switchTo().frame("left");
        //取得iframe元素
        WebElement weiBoIframe. =driver.findElement(By.tagName("iframe"));
        //获得iframe窗口
        driver.switchTo().frame(weiBoIframe);


    在指定时间内定位到指定元素。
        WebElement message = wait.until
        New ExpectedCondition<WebElement>(){
                 PublicWebElementapply(WebDriver d){
                           Return d.findElement(By.cssSelector(“#myDiv p”))
                     }
                }
        );


    等待元素的属性值改变
        Boolean classname = wait.until(
        New ExpectedCondition<Boolean>(){
                 PublicBooleanapply(WebDriver d){
                           Return d.findElement(By.cssSelector(“.jq-codeDemo p”))
                                             .getAttribute(“class”)
                                             .contains(“ohmy”);
                             }
                    }
                );

    等待元素变为可见
        (new WebDriverWait(driver, 10)).until(
        new ExpectedCondition<Boolean>(){
                          publicBooleanapply(WebDriver d){
                                    return d.findElement(By.id(“page4”)).isDisplayed();
                          }
                }
        );

    等待DOM的事件
        (new WebDriverWait(driver, 10)).until(
                 New ExpectedCondition<Boolean>(){
                     Public Boolean apply(WebDriver d){
                        JavascriptExecutor js = (JavascriptExecutor) d;
                        Return (Boolean) js.executeScript(“returnjQuery.active== 0”);
                     }
              }
           )



 */
public class SeleniumAPI {
    WebDriver driver;
    String url="";

    @Test
    public void seleniumApi(){

    }

    @BeforeMethod
    public void startDriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver=new ChromeDriver();
    }

    @AfterMethod
    public void quitDriver(){
        driver.quit();
    }


}




