# 时间流

## 开发文档
[开发文档](https://github.com/LuoHaomin/TimeManager/blob/Refactored/document.md)
## 产品介绍
我们的目标非常明确：致力于为中国科学技术大学学生提供一个便捷、智能、高效与集成的日程管理工具。作为科大学生，我们深知在学习和生活中，合理安排时间的重要性。因此，我们希望通过这款应用，为广大科大学子提供一站式的服务，包括课程表、校车班次以及二课日程的查询与管理。

本软件在应用架构方面，采用了三层架构，分别是数据层、逻辑层和表示层（UI层）。  
数据层基于SQLite以及相应Android库实现，负责存储和管理用户的日程与计划信息；  
逻辑层负责处理各种业务逻辑，包括智能安排日程、查询和管理日程等，主要由DailySchedule.class（日程规划类）实现；  
表示层则负责展示界面和与用户进行交互，即用户所能看到的UI界面。

## 项目进程
本项目由罗浩民最开始提出构想，致力于打造适合科大学生的时间管理助手，通过拆分、细分计划，智能安排，最终达成计划。  
十月中旬时，王子宁加入项目开发，在原有基础上加入日程管理，自我评价等模块。  
在具体实现项目的过程中，罗浩民搭建了完整的计划、日程数据库，帮助用户存储管理使用日记与数据；  
引入计划落实细分的框架，规划了软件底层逻辑，使软件开发更直观，维护更便捷；实现了宏观时间预览的构想，使计划有了清晰的概览；  
同时提出信息过滤和AI接口的理念，使软件逐步走向智能化与高效化。

王子宁引入了日程安排与展示，设计并绘制软件图标，加入番茄钟接口，具体细化日程分配，使用户在一个页面即可使用软件的大部分功能；  
引入自我评价机制，搭建自我评价数据库（尚在开发中），使用户的自我反馈更加直观；  
在每日日程安排中引入校车班次自动添加，将不同类别功能集中在同一页面，使软件更美集成，功能更科大。

## 宣传
https://github.com/LuoHaomin/TimeManager/assets/147121970/10ba457b-f77d-40f0-9d9e-f25e1793b64e

## 发布预告
1月20日发布1.0（alpha）版。
## 备注
### 该项目为大一学生的大作业，开发者水平有限，如有使用不便，还请多多包涵。
