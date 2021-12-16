# 基础镜像
FROM java:8
# 将当前目录下的jar包复制到docker容器的目录下
ADD works-1.0-SNAPSHOT.jar /works.jar
# 运行过程共创建一个project.jar文件
RUN bash -c 'touch /works.jar'
# 声明服务运行端口
EXPOSE 8888
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar", "/works.jar"]
# 指定维护者的名字
MAINTAINER zhangls
