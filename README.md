# SimpleNovel

   
                                             SimpleNovel 简说 
  
  
   一个以服务端利用 Jsoup 通过解析Html页面爬取数据，作为小说阅读器的数据来源的软件，页面主要模仿 “追书神器” 。
   
        （1）客户端
            1). 基础界面的编写
            2). 书城界面
            3). 分类界面
            4). 第三方工具的使用Litepal,Glide,OKHttp.
            
            
        （2）服务端（主要用到了Serverlet）
            1）. 通过Jsoup 解析Html获取网络上小说对应的章节名称与章节内容
            2）. 通过Jsoup 解析Html获取起点小说网的排行榜，分类等信息作为客户端的数据来源
            3）. 将获取的数据存储到数据库中
            4）. 通过fastJson 将数据以Json的数据格式响应给客户端
            
            
  总结，到目前为止项目尚未完善
  
       1. 服务端爬取的数据的不稳定性，导致客户端请求数据会出现缺失的情况
       2. 界面图标尚未完善
       3. 个人用户信息未完善
       4. 项目尚未进行适配
       
       
   项目截图:
   
   
   小说阅读模块：
   
   ![小说阅读模块](/readPage.png)      
   
   小说阅读设置模块：
   
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/readPageSetting.png)   
   
   
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/readPageSetting1.jpg) 
   
   
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/readPageSetting2.jpg)    
   
   书城 :
   
   
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/bookCity.jpg)    
   
   
    
   书本查找 ：
    
    
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/bookSearch.jpg)    
    
    
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/bookSearchResult.jpg)   
   
    
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/bookDetail.jpg)   
    
 
    书本排行 ：
    
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/bookRank.jpg)   
    
    
    
    书本分类 ：
    
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/bookType.jpg)   
   
   
   ![小说阅读模块](https://github.com/pressureKai/SimpleNovel/blob/master/bookTypeDetail.jpg)   
    
    
    
    
   
   
