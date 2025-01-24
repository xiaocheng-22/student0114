<template>
  <div id="main">
    <el-container style="height: 100vh; border: 1px solid #eee">
      <el-aside width="220px" style="background-color: rgb(238, 241, 246);">
        <el-menu :default-openeds="['1','2','3']">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-menu"></i><span>功能项</span></template>
            <el-menu-item index="1-1" @click="isShow=1">学生信息</el-menu-item>
            <el-menu-item index="1-2" @click="isShow=2">成绩录入</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <span>用户：</span>
          <span>{{username}}</span>
        </el-header>

        <el-main v-show="isShow==1">
          <StudentInfo></StudentInfo>
        </el-main>

        <el-main v-show="isShow==2">
          <el-row :gutter="20" justify="space-between">
            <el-col :span="12">
              <el-button type="primary" @click="addShow=true">新增</el-button>
            </el-col>
            <el-col :span="12" style="text-align: right;">
              <el-input placeholder="姓名" v-model="searchName" style="width:120px;" />&nbsp;
              <el-button type="primary" @click="handleSearch">查询</el-button>
            </el-col>
          </el-row>

          <el-dialog
              title="新增"
              :visible.sync="addShow"
              @closed="closeWin"
              width="30%">
             <el-form ref="form" label-width="85px">
               <el-form-item label="姓名：">
                 <el-input v-model="newScore.name"></el-input>
               </el-form-item>
               <el-form-item label="语文：">
                 <el-input v-model="newScore.chi"></el-input>
               </el-form-item>
               <el-form-item label="数学：">
                 <el-input v-model="newScore.math"></el-input>
               </el-form-item>
               <el-form-item label="英语：">
                 <el-input v-model="newScore.eng"></el-input>
               </el-form-item>
             </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="addShow=false" type="warning">取 消</el-button>
              <el-button type="primary" @click="addScore">添 加</el-button>
            </span>
          </el-dialog>

          <el-table :data="score_for_page" stripe height="calc(100% - 80px)">
            <el-table-column label="序号" width="60">
              <template slot-scope="scope">
                {{ currentPage*pageSize-pageSize+(scope.$index + 1) }}
              </template>
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="140">
              <template slot-scope="scope">
                <el-input
                    v-if="scope.row.editing"
                    v-model="scope.row.name"
                ></el-input>
                <span v-else>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="chi" label="语文" width="140">
              <template slot-scope="scope">
                <el-input
                    v-if="scope.row.editing"
                    v-model="scope.row.chi"
                ></el-input>
                <span v-else>{{ scope.row.chi }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="math" label="数学" width="140">
              <template slot-scope="scope">
                <el-input
                    v-if="scope.row.editing"
                    v-model="scope.row.math"
                ></el-input>
                <span v-else>{{ scope.row.math }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="eng" label="英语" width="140">
              <template slot-scope="scope">
                <el-input
                    v-if="scope.row.editing"
                    v-model="scope.row.eng"
                ></el-input>
                <span v-else>{{ scope.row.eng }}</span>
              </template>
            </el-table-column>

            <el-table-column label="操作">
              <template slot-scope="scope">
                <template  v-if="scope.row.editing">
                  <el-button
                      size="mini"
                      @click="handleCancel(scope.$index, scope.row)">取消</el-button>
                  <el-button
                      size="mini"
                      type="success"
                      @click="handleSave(scope.$index, scope.row)">保存</el-button>
                  </template>
                <template v-else>
                  <el-button
                      size="mini"
                      @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                  <el-button
                      size="mini"
                      type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </el-main>



      </el-container>
    </el-container>
  </div>
</template>

<script>

import axios from "axios";
// 导入子组件
import StudentInfo from "./StudentInfo"

export default {
  name: 'StudentMain',
  components: {
    // 注册子组件
    StudentInfo
  },
  data(){
    return {
      scoreData: [],
      initScoreData:[],
      isShow: 1,
      username: sessionStorage.getItem('username'),
      addShow:false,
      newScore:{                         // 新增对象
        number:"",
        name:"",
        age:"",
        chi:"",
        math:"",
        eng:"",
      },
      currentPage: 1, // 当前页
      pageSize: 10, // 每页显示条数
      total: 0, // 总条目数
      searchName:''
    }
  },
  created() {

  },
  mounted() {
    this.getScoreData();
  },
  computed:{
    score_for_page(){    //  分页
      return this.scoreData.slice(this.currentPage*this.pageSize - this.pageSize,this.currentPage*this.pageSize) ;
    }
  },
  methods:{
    getScoreData(){
       let me=this;
       axios({
         url:'http://localhost:9090/students',
         method:'GET'
       }).then(res=>{
         this.scoreData=res.data;
         this.initScoreData=res.data;
         me.total=res.data.length;
       })
    },
    addScore(){
      let me=this;
      if(this.newScore.name!=''&&this.newScore.chi!=''&&this.newScore.math!=''&&this.newScore.eng!=''){
          axios({
            url:'http://localhost:9090/add',
            method:'POST',
            data:this.newScore
          }).then(res=>{
            if(res.data.code==200){
              this.$alert('添加成功！', '提示', {
                confirmButtonText: '确定',
                callback: function (){
                  me.addShow=false;
                  me.getScoreData();
                }
              });
            }
          });
       }else{
         this.$alert('请将数据补充完整！', '提示', {
           confirmButtonText: '确定',
           callback: function (){}
         });
       }
    },
    closeWin(){
      this.newScore={
        number:"",
        name:"",
        age:"",
        chi:"",
        math:"",
        eng:""
      }
    },
    handleEdit(index,row){
      console.log(index+','+row.id);
      this.$set(row, 'editing', true)
    },
    handleDelete(index,row){
      console.log(index+','+row.id);
      let me=this;
      this.$confirm('确认删除？')
      .then(() => {
        axios({
          url:'http://localhost:9090/delete',
          method:'POST',
          data:row
        }).then(function(){
          me.getScoreData();
        })
      })
      .catch(()=> {});
    },
    handleSizeChange(a){
      this.pageSize=a;
    },
    handleCurrentChange(a){
      this.currentPage=a;
    },
    handleSave(index,row){
      console.log('保存'+index+','+row)
      let me=this;
      this.$set(row, 'editing', false);
      axios({
        url:'http://localhost:9090/update',
        method:'POST',
        data:row
      }).then(function (){
        me.getScoreData();
      })
    },
    handleCancel(index,row){
      this.$set(row, 'editing', false);
      this.getScoreData();
    },
    handleSearch(){
       axios({
         url:'http://localhost:9090/search',
         method:'POST',
         data:{name:this.searchName}
       }).then(res=>{
         this.scoreData=res.data;
       })
    }
  }
}
</script>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>
