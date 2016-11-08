# storm-kafka-zookeeper clusters

setup.sh 是 storm-kafka-zookeeper 集群环境 的一键部署脚本

## usage

用你的账户执行 `./setup.sh`, 将安装到 /home/$USER/work 下, 不要用 `sudo` 或 root 用户, 那将安装到 /home/root/work, 且必须是 root 权限才能执行, 安装到最后的时候会要求 sudo 权限以开启必须的端口

本目录 目录结构

```
.
|-- boot                          # 一些便捷脚本
|   |-- ...
|   `-- ...
|-- conf
|   |-- server.properties
|   `-- zoo.cfg
|-- config                        # 个性化配置文件
|-- kafka
|   `-- put_kafka_tgz_here        # kafka 安装包存放位置
|-- README.md
|-- setup.sh                      # 部署环境脚本
|-- storm
|   `-- put_storm_tar_gz_here     # storm 安装包存放位置
`-- zookeeper
    `-- put_zookeeper_tar_gz_here # zookeeper 安装包存放位置
```

/home/$USER/work 目录结构

```
.
|-- apache-storm-1.0.2            # storm 安装目录
|   |-- ...
|   `-- ...
|-- boot                          # 一些便捷脚本
|   |-- ...
|   `-- ...
|-- kafka_2.11-0.9.0.1            # kafka 安装目录
|   |-- ...
|   `-- ...
|-- kafka-logs                    # kafka 日志目录
|-- storm-workdir                 # storm 工作目录
|-- zookeeper
|   |-- data                      # zookeeper 工作目录
|   `-- logs                      # zookeeper 日志目录
`-- zookeeper-3.4.9               # zookeeper 安装目录
    |-- ...
    `-- ...
```
