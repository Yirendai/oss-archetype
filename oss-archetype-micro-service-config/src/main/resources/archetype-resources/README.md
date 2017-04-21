# ${artifactId}

开发和测试环境使用的通用配置.

# 开启git hooks方式

git hooks的文件现在放在项目根目录下的 `hooks` 目录,如果想要开启本地 hooks 功能,需要进行如下设置: 

## git version < 2.9
+ 对于低版本的git，建立从项目目录到.git/hooks目录下的软链接,比如: `ln -sf $(pwd)/hooks/pre-commit $(pwd)/.git/hooks/pre-commit`
+ 该方式使用不便，而且 windows 开发平台对软链接的支持很弱。  

## git version >= 2.9
+ 使用git config --local hooksPath hooks，设置hooks脚本目录即可。  

