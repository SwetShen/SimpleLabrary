const mysql = require('mysql');

const db = mysql.createConnection({
  host:"localhost",//数据库ip地址 3306
  user:"root",//数据库的账户
  password:"root",//数据库的密码
  database:"library"//数据库
})

//连接数据库
db.connect((err) =>{
  if(err) throw err;
  console.log('mysql connect')
})

let query = sql => {
  return new Promise((resolve,reject) => {
    db.query(sql,(err,result)=>{
      if(err) reject(err);
      resolve(result);
    })
  }).catch(err => {
    console.log(err);
  })
}

let queryParam = (sql,param) => {
  return new Promise((resolve,reject) => {
    db.query(sql,param,(err,result)=>{
      if(err) reject(err);
      resolve(result);
    })
  }).catch(err => {
    console.log(err);
  })
}


module.exports = {
  query,
  queryParam
}