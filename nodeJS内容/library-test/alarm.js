const Koa = require('koa');
const app = new Koa();
const Router = require('koa-router');
const router = new Router();
const koaBody = require('koa-body');
const path = require('path');
const fs = require('fs');
const static = require('koa-static');
//一定要导入mysql工具
const tool = require('./mysql-tool');

app.use(koaBody({
  multipart:true,
  formidable:{
    maxFileSize:200*1024*1024
  }
}))


router.get("/start",async(ctx,next) => {
  await next();
  let time = ctx.query['time'];
  let start_time = new Date();
  let start = start_time.format('yyyy-MM-dd hh:mm:ss');
  await tool.query(`insert into test(name,start) value ('洗衣机03','${start}')`)
  if(time == '' || time == undefined){
    return;
  }
  let end = new Date(start_time.getTime() + parseInt(time)).format('yyyy-MM-dd hh:mm:ss');
  setTimeout(()=>{
    tool.query(`update test set end='${end}' where name='洗衣机03'`);
  },time);
  
})

router.get("/start1",async(ctx,next) => {
  await next();
  let time = ctx.query['time'];
  let start_time = new Date();
  let start = start_time.format('yyyy-MM-dd hh:mm:ss');
  await tool.query(`insert into test(name,start) value ('洗衣机04','${start}')`)
  if(time == '' || time == undefined){
    return;
  }
  let end = new Date(start_time.getTime() + parseInt(time)).format('yyyy-MM-dd hh:mm:ss');
  setTimeout(()=>{
    tool.query(`update test set end='${end}' where name='洗衣机04'`);
  },time);
  
})



Date.prototype.format = function(fmt) { 
  var o = { 
     "M+" : this.getMonth()+1,                 //月份 
     "d+" : this.getDate(),                    //日 
     "h+" : this.getHours(),                   //小时 
     "m+" : this.getMinutes(),                 //分 
     "s+" : this.getSeconds(),                 //秒 
     "q+" : Math.floor((this.getMonth()+3)/3), //季度 
     "S"  : this.getMilliseconds()             //毫秒 
 }; 
 if(/(y+)/.test(fmt)) {
         fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
 }
  for(var k in o) {
     if(new RegExp("("+ k +")").test(fmt)){
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      }
  }
 return fmt; 
}     


app.use(router.routes());
app.listen(3000);