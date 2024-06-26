# 项目结构
## 概览
+ activity
+ fragment
+ database
+ adapter
+ bean
+ util

## 详细
### activity
+ HelloActivity.java
  - 欢迎界面：启动时显示，兼具初始化与授权功能
+ MainActivity.java
  - 主界面：显示主要功能，包括导航栏、PagerView、底部导航栏
  - 其中，PagerView包含多个Fragment：PlanFragment、ScheduleFragment、MonthFragment、EvaluationFragment
+ EditPlanActivity.java
  - 计划编辑界面：编辑计划，包括计划名称、计划内容、计划时间、计划提醒
+ EditScheduleActivity.java
  - 日程编辑界面：编辑日程，包括日程名称、日程内容、日程时间、日程提醒


### fragment
+ PlanFragment.java
  - 计划界面：显示计划列表，包括计划名称、计划内容、计划时间、计划提醒
+ ScheduleFragment.java
  - 日程界面：显示日程列表，包括日程名称、日程内容、日程时间、日程提醒
+ MonthFragment.java
  - 月历界面：显示月历，包括月历视图、月历事件
+ EvaluationFragment.java
  - 评价界面：显示评价列表，包括评价内容、评价时间

### database
+ DB_Plan.java
  - 计划数据库：存储计划信息，包括计划名称、计划内容、计划时间、计划提醒
+ DB_Schedule.java
  - 日程数据库：存储日程信息，包括日程名称、日程内容、日程时间、日程提醒
+ DB_Evaluation.java
  - 评价数据库：存储评价信息，包括评价内容、评价时间

### adapter

### bean

### util

---
# 项目开发计划
## 需求分析
+ 项目名称：日程管理系统
+ 项目功能：
  - 计划管理：添加、编辑、删除计划
  - 日程管理：添加、编辑、删除日程
  - 月历视图：显示月历，包括月历事件
  - 评价管理：添加、编辑、删除评价
+ 目标对象
  - 个人用户：管理个人计划、日程、评价

+ 项目特点
+ 项目目标
  - 个人用户：提高个人时间管理效率

## 技术选型
+ 开发工具：Android Studio
+ 开发语言：Java
+ 数据库：SQLite
+ 项目管理：Git
+ 项目构建：Gradle
+ 模式设计：MVC

## 开发计划

## 已完成功能
## 待完成功能
## 待重构功能
## 待优化功能
## 待测试功能


---
# 项目开发日志
