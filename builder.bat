:: 修改版本号,服务名称,docker仓库地址
gae run -v 3.0.0 -n user-service -h 192.168.50.241:30002/docker/ -f #{n}-#{v}-jar-with-dependencies.jar