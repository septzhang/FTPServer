# FTPServer
基于Spring boot的FTP项目

# Server 端功能需求：

## 上传文件接口：

   响应标准 http 协议的 post 请求发送来的文件，接收到文件后将文件重命名
   （使用 UUID）并按照日期保存至服务器文件系统的不同的目录中（目录格
   式 yyyyMMdd），同时将文件大小、文件类型，原始文件名、创建时间、文
   件保存目录地址等元数据记录至数据库中，同时将 UUID 返回给客户端。

## 下载文件接口：

   响应客户端获取文件流的 get 请求，客户端参数为接口 1 中返回的 UUID，
   在响应中写入文件流，无其他返回值，异常响应时返回 410 状态码。

## 获取文件元数据接口：

   响应客户端 get 请求，客户端参数为接口 1 中返回的 UUID，返回值为 Json
   格式的元数据信息。

# Server 端技术要求：

   项目基于 Maven 构建;
   WEB 服务使用嵌入式 Jetty 或 Tomcat 实现;
   只提供接口服务，无界面;
   可以使用任意第三方框架和库.

# 接口文档

## /file

```text
FTP服务
```

#### 公共Header参数

| 参数名   | 示例值 | 参数描述 |
| -------- | ------ | -------- |
| 暂无参数 |        |          |

#### 公共Query参数

| 参数名   | 示例值 | 参数描述 |
| -------- | ------ | -------- |
| 暂无参数 |        |          |

#### 公共Body参数

| 参数名   | 示例值 | 参数描述 |
| -------- | ------ | -------- |
| 暂无参数 |        |          |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /file/文件下载接口

```text
GET请求，使用FileUUID获取文件
```

#### 接口状态

> 已完成

#### 接口URL

> http://localhost:8080/download?fileUUID=6b630f2bff8f4d119f1202bab4a9991

#### 请求方式

> GET

#### Content-Type

> form-data

#### 请求Query参数

| 参数名   | 示例值                          | 参数类型 | 是否必填 | 参数描述       |
| -------- | ------------------------------- | -------- | -------- | -------------- |
| fileUUID | 6b630f2bff8f4d119f1202bab4a9991 | Text     | 是       | 指定文件的UUID |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

#### 成功响应示例

```javascript
�PNG
{"msg":"下载成功6b630f2bff8f4d119f1202bab4a9991b.png","code":0}
```

| 参数名 | 示例值                                       | 参数类型 | 参数描述     |
| ------ | -------------------------------------------- | -------- | ------------ |
| msg    | 下载成功6b630f2bff8f4d119f1202bab4a9991b.png | Text     | 返回文字描述 |
| code   | 0                                            | Text     | 状态码       |

#### 错误响应示例

```javascript
{
	"msg": "异常响应",
	"code": 410
}
```

| 参数名 | 示例值   | 参数类型 | 参数描述     |
| ------ | -------- | -------- | ------------ |
| msg    | 异常响应 | Text     | 返回文字描述 |
| code   | 410      | Text     | 状态码       |

## /file/文件查询接口

```text
GET请求获取文件的信息
```

#### 接口状态

> 已完成

#### 接口URL

> http://localhost:8080/getInfo?fileUUID=6b630f2bff8f4d119f1202bab4a9991b

#### 请求方式

> GET

#### Content-Type

> form-data

#### 请求Query参数

| 参数名   | 示例值                           | 参数类型 | 是否必填 | 参数描述       |
| -------- | -------------------------------- | -------- | -------- | -------------- |
| fileUUID | 6b630f2bff8f4d119f1202bab4a9991b | Text     | 是       | 指定文件的UUID |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

#### 成功响应示例

```javascript
{
	"msg": "success",
	"code": 0,
	"file": {
		"fileId": 108,
		"fileSize": 5709,
		"fileType": "image/png",
		"fileOldName": "cat.png",
		"fileCreatetime": "2022-02-12T16:03:37.000+00:00",
		"filePath": "E:\\resources\\temp\\20220213\\6b630f2bff8f4d119f1202bab4a9991b.png",
		"fileUuid": "6b630f2bff8f4d119f1202bab4a9991b"
	}
}
```

| 参数名              | 示例值                                                       | 参数类型 | 参数描述           |
| ------------------- | ------------------------------------------------------------ | -------- | ------------------ |
| msg                 | success                                                      | Text     | 返回文字描述       |
| code                | 0                                                            | Text     | 状态码             |
| file                | -                                                            | Text     | 需要上传的单个文件 |
| file.fileId         | 108                                                          | Text     | 文件的主键id       |
| file.fileSize       | 5709                                                         | Text     | 文件大小           |
| file.fileType       | image/png                                                    | Text     | 文件类型           |
| file.fileOldName    | cat.png                                                      | Text     | 文件原始名字       |
| file.fileCreatetime | 2022-02-12T16:03:37.000+00:00                                | Text     | 文件创建时间       |
| file.filePath       | E:\resources\temp\20220213\6b630f2bff8f4d119f1202bab4a9991b.png | Text     | 文件存储路径       |
| file.fileUuid       | 6b630f2bff8f4d119f1202bab4a9991b                             | Text     | 文件的UUID名       |

#### 错误响应示例

```javascript
{
	"msg": "文件未找到",
	"code": 406
}
```

| 参数名 | 示例值     | 参数类型 | 参数描述     |
| ------ | ---------- | -------- | ------------ |
| msg    | 文件未找到 | Text     | 返回文字描述 |
| code   | 406        | Text     | 状态码       |

## /file/文件上传接口

```text
上传带个文件file
```

#### 接口状态

> 已完成

#### 接口URL

> http://localhost:8080/upload

#### 请求方式

> POST

#### Content-Type

> form-data

#### 请求Body参数

| 参数名 | 示例值                                        | 参数类型 | 是否必填 | 参数描述           |
| ------ | --------------------------------------------- | -------- | -------- | ------------------ |
| file   | C:\Users\septzhang\OneDrive\图片\素材\cat.png | File     | 是       | 需要上传的单个文件 |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

#### 成功响应示例

```javascript
{
	"msg": "cat.png文件上传成功",
	"code": 0
}
```

| 参数名 | 示例值              | 参数类型 | 参数描述     |
| ------ | ------------------- | -------- | ------------ |
| msg    | cat.png文件上传成功 | Text     | 返回文字描述 |
| code   | 0                   | Text     | 状态码       |
