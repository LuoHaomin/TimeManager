pluginManagement {
    repositories {
        google()
        mavenCentral()
        
//        add domestic mirror urls
//        maven{url 'https://maven.aliyun.com/repository/jcenter' }
//        maven{url 'https://maven.aliyun.com/repository/google' }
//        maven{url 'https://maven.aliyun.com/repository/gradle-plugin' }
//        maven{url 'https://maven.aliyun.com/repository/public' }
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //        add domestic mirror urls
//        maven{url 'https://maven.aliyun.com/repository/jcenter' }
//        maven{url 'https://maven.aliyun.com/repository/google' }
//        maven{url 'https://maven.aliyun.com/repository/gradle-plugin' }
//        maven{url 'https://maven.aliyun.com/repository/public' }
    }
}

rootProject.name = "TimeManager"
include(":app")
