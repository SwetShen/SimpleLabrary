const Koa = require('koa');
const app = new Koa();
const Router = require('koa-router');
const router = new Router();
const cors = require('koa2-cors');
const koaBody = require('koa-body');
const path = require('path');
const fs = require('fs');
const static = require('koa-static');
//一定要导入mysql工具
const tool = require('./mysql-tool');

app.use(cors());
app.use(koaBody({
  multipart:true,
  formidable:{
    maxFileSize:200*1024*1024
  }
}))

app.use(static(__dirname +'/static/'))

//判断用户名是否存在
router.post('/exists/user',async (ctx,next) =>{
  await next();
  
  let uid = ctx.request.body['uid'];
  // console.log(uid);
  if(uid == null || uid == undefined){
    ctx.body = {'err':'empty uid'};
    return;
  }
  let result = await tool.query(`select count(*) from user where uid=${uid}`);
  // console.log(result);
  ctx.body = result[0]['count(*)'];
})

// 登录 用户 管理员
router.post('/login',async (ctx,next) => {
  await next();
  let uid = ctx.request.body['uid'];
  let upassword = ctx.request.body['upassword'];
  let usite = ctx.request.body['usite'] +'';
  if(uid == undefined || uid == ''){
    ctx.body = {'err':'empty uid'};
    return;
  }
  if(upassword == undefined || upassword == ''){
    ctx.body = {'err':'empty upassword'};
    return;
  }
  // console.log(usite == undefined)
  if(usite == undefined || usite == ''){
    ctx.body = {'err':'empty usite'};
    return;
  }
  //注意：uid usite在数据库都是int
  //第一种写法
  // let result = await tool.query(`select * from user where uid=${parseInt(uid)} and upassword=${upassword} and usite=${parseInt(usite)}`);
  // console.log(parseInt(uid)+":"+upassword+":"+parseInt(usite))
  let params = [parseInt(uid),upassword,parseInt(usite)];
  let result = await tool.queryParam(`select * from user where uid=? and upassword=? and usite=?`,params);
  ctx.body = result;
})

//注册 用户
router.post("/reg",async(ctx,next) =>{
  await next();
  let uid = ctx.request.body['uid'];
  let upassword = ctx.request.body['upassword'];
  let usite = 0;
  let uname = ctx.request.body['uname'];
  console.log(uname);
  let uimg = ctx.request.files.uimg;
  //必须判断的选项
  if(uid == undefined || uid == ''){
    ctx.body = {'err':'empty uid'};
    return;
  }
  if(upassword == undefined || upassword == ''){
    ctx.body = {'err':'empty upassword'};
    return;
  }
  if(uname == undefined || uname == ''){
    uname == '未命名';
  }
  if(uimg == undefined || uimg == ''){
    uimg == '/upload/a.jpg';
  }
  if(uimg.size == 0){
    uimg = null;//没有图片
    let params =  [parseInt(uid),uname,upassword,usite,uimg];
    let result = await tool.queryParam('insert into user value(?,?,?,?,?)',params);
    ctx.body = result;
  }else{//有图片上传
    ctx.body = await new Promise((resolve,reject) => {
      const reader = fs.createReadStream(uimg.path);//bimg.path 起始位置
      let filePath = path.join(__dirname,'/static/upload/') + `${uimg.name}`;//目标位置
      const upStream = fs.createWriteStream(filePath);
      reader.pipe(upStream);
      resolve('/upload/' + `${uimg.name}`);//将图片保存的目标位置路径发送给then后面的res
    }).then(res => {
      let params =  [parseInt(uid),uname,upassword,usite,res];
      let result = tool.queryParam('insert into user value(?,?,?,?,?)',params);
      return Promise.resolve(result);
    }).catch(err => {
      console.log(err);//处理异常
    })
  }
})

router.get("",async(ctx,next)=>{
  await next();
  ctx.redirect("/query");
})

//查询 1、按照输入相关字段  2、根据id
router.get("/query",async(ctx,next) => {
  await next();
  let name = ctx.query['name'];
  let id = ctx.query['id'];
  let result;
  if(name != undefined && name != ''){
    name = '%' + name + '%';
    //根据name查询内容
    let params = [name,name,name]
    result = await tool.queryParam(`select * from book where bname like ? or bauthor like ? or btype like ?`,params);
  }else{
    if(id != undefined && id != ''){
      //根据id查询内容
      //id是int类型
      result = await tool.query(`select * from book where bid=${parseInt(id)}`);
    }else{
      //查询所有内容
      result = await tool.query(`select * from book`);
    }
  }
  ctx.body = result;

})

// 增加书籍 管理员
router.post("/add",async(ctx,next) => {
  await next();
  let bid = null;
  let bname = ctx.request.body['bname'];
  let bauthor = ctx.request.body['bauthor'];
  let btype = ctx.request.body['btype'];
  let bdesc = ctx.request.body['bdesc'];
  let bcount = ctx.request.body['bcount'];
  let bimg = ctx.request.files.bimg;
  if(bname == undefined || bname == ''){
    ctx.body = {'err':'empty bname'};
    return;
  }
  if(btype == undefined || btype == ''){
    ctx.body = {'err':'empty btype'};
    return;
  }
  if(bcount == undefined || bcount == ''){
    ctx.body = {'err':'empty bcount'};
    return;
  }
  if(bauthor == undefined || bauthor == ''){
    bauthor = '未命名';
  }
  if(bdesc == undefined || bdesc == ''){
    bdesc = '空';
  }
  //bimg.size == 0 没有图片上传
  if(bimg.size == 0){
    bimg = null;//没有图片
    let params = [bid,bname,bauthor,btype,bdesc,bcount,bimg];
    let result = await tool.queryParam("insert into book value(?,?,?,?,?,?,?)",params);
    ctx.body = result;
  }else{//有图片上传
    ctx.body = await new Promise((resolve,reject) => {
      const reader = fs.createReadStream(bimg.path);//bimg.path 起始位置
      let filePath = path.join(__dirname,'/static/upload/') + `${bimg.name}`;//目标位置
      const upStream = fs.createWriteStream(filePath);
      reader.pipe(upStream);
      resolve('/upload/' + `${bimg.name}`);//将图片保存的目标位置路径发送给then后面的res
    }).then(res => {
      let params = [bid,bname,bauthor,btype,bdesc,bcount,res];
      let result = tool.queryParam("insert into book value(?,?,?,?,?,?,?)",params);
      return Promise.resolve(result);
    }).catch(err => {
      console.log(err);//处理异常
    })
  }

  
  
})

// 删除书籍 管理员
router.post("/delete",async(ctx,next) => {
  await next();
  let id = ctx.request.body['id'];
  if(id == undefined || id == ''){
    ctx.body = {'err':'empty id'};
    return;
  }
  let result = await tool.query(`delete from book where bid=${parseInt(id)}`);
  ctx.body = result;
})

// 修改书籍 管理员
// 修改书籍之前 得查询这个书籍得信息
// 修改方式 ：1、已知内容，然后修改  2、未知内容，修改
router.post("/update",async(ctx,next) => {
  await next();
  let bid = ctx.request.body['bid'];
  let bname = ctx.request.body['bname'];
  let bauthor = ctx.request.body['bauthor'];
  let btype = ctx.request.body['btype'];
  let bdesc = ctx.request.body['bdesc'];
  let bcount = ctx.request.body['bcount'];
  let bimg = ctx.request.body['bimg'];
  if(bid == undefined || bid == ''){
    ctx.body = "{'error':'empty bid'}";
    return;
  }
  if(bname == undefined || bname == ''){
    ctx.body = "{'error':'empty bname'}";
    return;
  }
  if(btype == undefined || btype == ''){
    ctx.body = "{'error':'empty btype'}";
    return;
  }
  if(bcount == undefined || bcount == ''){
    ctx.body = "{'error':'empty bcount'}";
    return;
  }
  if(bauthor == undefined || bauthor == ''){
    ctx.body = "{'error':'empty bauthor'}";
    return;
  }
  if(bdesc == undefined || bdesc == ''){
    ctx.body = "{'error':'empty bdesc'}";
    return;
  }
  if(bimg == undefined || bimg == ''){
    ctx.body = "{'error':'empty bimg'}";
    return;
  }

  let params = [bname,bauthor,btype,bdesc,bcount,bimg,bid]
  let result = await tool.queryParam("update book set bname=?,bauthor=?,btype=?,bdesc=?,bcount=?,bimg=? where bid=?",params);
  ctx.body = result;
})

//管理员 借书查询
router.get("/lend/admin/query",async(ctx,next) => {
  await next();
  let name = ctx.query['name'];
  let result;
  if(name == undefined || name == ''){
    result = await tool.query(`select * from (select l.uid,u.uname,l.bid,b.bname,lstart,lfinish
      from lend l,user u,book b 
      where l.uid = u.uid and l.bid = b.bid ) temp`)
  }else{
    name = '%' + name + '%';
    let params = [name,name];
    result = await tool.queryParam(`select * from (select l.uid,u.uname,l.bid,b.bname,lstart,lfinish
      from lend l,user u,book b 
      where l.uid = u.uid and l.bid = b.bid ) temp where uname like ? or bname like ?`,params)
  }
  ctx.body = result;
})

//用户 借书查询
router.get("/lend/user/query",async(ctx,next) => {
  await next();
  let uid= ctx.query['uid'];
  if(uid == undefined || uid == ''){
    ctx.body = {'error':'empty uid'}
    return;
  }
  let name = ctx.query['name'];
  let result;
  if(name == undefined || name == ''){
    result = await tool.query(`select * from (select l.uid,u.uname,l.bid,b.bname,lstart,lfinish
      from lend l,user u,book b 
      where l.uid = u.uid and l.bid = b.bid ) temp where uid=${parseInt(uid)}`)
  }else{
    name = '%' + name + '%';
    let params = [name,name,uid];
    result = await tool.queryParam(`select * from (select l.uid,u.uname,l.bid,b.bname,lstart,lfinish
      from lend l,user u,book b 
      where l.uid = u.uid and l.bid = b.bid ) temp where uname like ? or bname like ? having uid=?`,params)
  }
  ctx.body = result;
})

//用户的借书
router.post("/lend/add",async(ctx,next) => {
  await next();
  let uid = ctx.request.body["uid"];
  let bid = ctx.request.body["bid"];
  let lstart = new Date(); //获取当前的服务器的时间
  let lfinish = '2000-01-01 00:00:01';
  if(uid == undefined || uid == ''){
    ctx.body = {'error':'empty uid'}
    return;
  }
  if(bid == undefined || bid == ''){
    ctx.body = {'error':'empty bid'}
    return;
  }
  let params = [uid,bid,lstart,lfinish];
  let result = await tool.queryParam(`insert into lend value (?,?,?,?)`,params);
  // 对应的书籍少1
  await tool.query(`update book set bcount=bcount-1 where bid=${bid}`)
  ctx.body = result;
})

//用户查询自己是否有借过书
router.get("/lend/user/exist",async(ctx,next) => {
  await next();
  let uid= ctx.query['uid'];
  let bid = ctx.query['bid'];
  if(uid == undefined || uid == ''){
    ctx.body = {'error':'empty uid'}
    return;
  }
  if(bid == undefined || bid == ''){
    ctx.body = {'error':'empty bid'}
    return;
  }
  let lfinish = '2000-01-01 00:00:01';//没有还书
  let params = [uid,bid,lfinish]
  let result = await tool.queryParam(`select * from lend where uid=? and bid=? and lfinish=?`,params)
  ctx.body = result;
})

//查询所有用户
router.post("/admin/query/user",async (ctx,next) =>{
  await next();
  let uid = ctx.request.body['uid'];//准确查询
  let uname = ctx.request.body['uname'];//模糊查询
  let result;
  if(uname != undefined && uname != null){
    let params = ['%' + uname + '%'];
    result = await tool.queryParam(`select * from user where uname like ?`,params);
  }else{
    if(uid != undefined && uid != null){
      result = await tool.query(`select * from user where uid = ${parseInt(uid)}`)
    }else{
      result = await tool.query(`select * from user`);
    }
  }
  ctx.body = result;
} )

//用户查询商品
router.get('/goods/query',async(ctx,next) => {
  await next();
  let result = await tool.query("select * from goods");
  ctx.body = result;
})

//用户添加购物车
router.post('/goods/add',async (ctx,next) => {
  await next();
  let uid = ctx.request.body['uid'];
  let gid = ctx.request.body['gid'];
  let shopnumber = ctx.request.body['shopnumber'];
  if(uid == undefined || uid == ''){
    ctx.body = {'error':'empty uid'}
    return;
  }
  if(gid == undefined || gid == ''){
    ctx.body = {'error':'empty gid'}
    return;
  }
  if(shopnumber == undefined || shopnumber == ''){
    shopnumber = 1;
  }
  let shopdate = new Date();
  let params = [parseInt(uid),parseInt(gid),parseInt(shopnumber),shopdate];
  let n = await tool.query(`select count(*) from shopcar where uid=${uid} and gid=${gid}`);
  let result;
  if(n[0]['count(*)'] <= 0){
    result = await tool.queryParam(`insert into shopcar(uid,gid,shopnumber,shopdate) value(?,?,?,?)`,params);
  }else{
    result = await tool.query(`update shopcar set shopnumber = shopnumber + 1 where uid=${uid} and gid=${gid}`)
  }
  ctx.body = result
})
//购物车数量增加1
 router.post("/goods/increase",async(ctx,next)=>{
   await next();
   let uid = ctx.request.body['uid'];
  let gid = ctx.request.body['gid'];
  if(uid == undefined || uid == ''){
    ctx.body = {'error':'empty uid'}
    return;
  }
  if(gid == undefined || gid == ''){
    ctx.body = {'error':'empty gid'}
    return;
  }
  let result = await tool.query(`update shopcar set shopnumber = shopnumber + 1
  where uid=${uid} and gid=${gid}`);
  ctx.body = result;
 })
 //购物车数量减去1
 router.post("/goods/decrease",async(ctx,next)=>{
  await next();
  let uid = ctx.request.body['uid'];
 let gid = ctx.request.body['gid'];
 if(uid == undefined || uid == ''){
   ctx.body = {'error':'empty uid'}
   return;
 }
 if(gid == undefined || gid == ''){
   ctx.body = {'error':'empty gid'}
   return;
 }
 let result = await tool.query(`update shopcar set shopnumber = shopnumber - 1
 where uid=${uid} and gid=${gid}`);
 ctx.body = result;
})
//用户查看购物车
router.get("/goods/car",async (ctx,next) =>{
  await next();
  let uid = ctx.query['uid'];
  let oid = ctx.query['oid'];
  let result;
  if(uid == undefined || uid == ''){
    ctx.body = {'error':'empty uid'}
    return;
  }
  if(oid == undefined || oid == ''){
    oid = null;
    result = await tool.query(`select s.gid,gname,gprice,shopnumber,shopdate 
    from shopcar s,goods g where s.gid = g.gid and uid=${uid} and oid is ${oid};`)
  }else{
    oid = null;
    result = await tool.query(`select s.gid,gname,gprice,shopnumber,shopdate 
    from shopcar s,goods g where s.gid = g.gid and uid=${uid} and oid is not${oid};`)
  }
  ctx.body = result;
})

//用户提交订单
router.post("/goods/order",async (ctx,next) =>{
  await next();
  let uid = ctx.request.body['uid'];
  let onumber = ctx.request.body['onumber'];
  let oprice = ctx.request.body['oprice'];
  let gids = ctx.request.body['gids'];
  if(uid == undefined || uid == ''){
    ctx.body = {'error':'empty uid'}
    return;
  }
  if(onumber == undefined || onumber == ''){
    ctx.body = {'error':'empty onumber'}
    return;
  }
  if(oprice == undefined || oprice == ''){
    ctx.body = {'error':'empty oprice'}
    return;
  }
  if(gids == undefined || gids == ''){
    ctx.body = {'error':'empty gids'}
    return;
  }
  let odate = new Date().format("yyyy-MM-dd hh:mm:ss");
  let params = [uid,onumber,oprice,odate]
  await tool.queryParam(`insert into shoporder(uid,onumber,oprice,odate) value(?,?,?,?)`,params);
  let result = await tool.query(`select oid from shoporder where uid=${uid} and odate='${odate}'`);
  console.log(gids);
  let oid = result[0]['oid'];
  if(oid){
    if(gids.length > 0){
      for(let gid of gids){
        await tool.query(`update shopcar set oid=${oid} where gid=${gid} and uid=${uid}`);
      }
    }
  }
  ctx.body = result;
})

//用户查看历史订单

//批量上传内容
// router.post("/many",async (ctx,next) => {
//   await next();
//   let array = ctx.request.body['array'];
//   console.log(array);
//   for(let item of array){
//     item = eval('(' +item+')');
//     let params = [item['name'],item['address'],item['number']];
//     await tool.queryParam('insert into test value(?,?,?)',params);
//   }
//   ctx.body = "";
// })

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