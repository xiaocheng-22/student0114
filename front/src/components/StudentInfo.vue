<template>
  <div id="main">
    <el-container>
      <el-aside style="width:200px;padding-top:5px;border-right:1px solid #e2e2e2;height:calc(100vh - 110px)">
          <!--default-expand-all全部展开，:expand-on-click-node禁止点击节点文本控制展开/折叠-->
          <el-tree :data="treeData" :props="defaultProps" @node-click="handleNodeClick" default-expand-all :expand-on-click-node="false" ></el-tree>
      </el-aside>
      <el-main style="padding-top:0px">
        <el-table
            :data="tableData"
            style="width: 100%">
          <el-table-column label="序号" width="60">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
              prop="number"
              label="学号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="name"
              label="姓名"
              width="180">
          </el-table-column>
          <el-table-column
              prop="className"
              label="班级">
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: 'StudentInfo',
  components: {

  },
  data(){
    return{
      treeData:[],
      defaultProps: {
        children: 'children',
        label: 'className'
      },
      tableData: []
    }
  },
  created() {
    this.buildTree();
    this.buildTable('','');
  },
  mounted() {

  },
  computed:{

  },
  methods:{
    handleNodeClick(node){
      //console.log(node)
      this.buildTable('',node.id);
    },
    async buildTree(){
      try {
        const data1 = await new Promise((resolve, reject) => {
           axios({
             url:'http://localhost:9090/classInfo',
             method:'GET',
             params:{
               className:''
             }
           }).then(res=>{
             resolve(res.data);
           }).catch(error=>{
             reject(error)
           })
        });
        let re=this.build(data1);
        //console.log(re)
        this.treeData=re;
      } catch (error) {
        console.error(error);
      }
    },
    // 转换为树形结构的方法
    build(data, parentId = 'root') {
      return data.filter(item => item.parentId == parentId)
          .map(item => ({
            ...item, //使用了对象展开运算符(...item) 保留原始数据项的所有属性
            children: this.build(data, item.id) // 递归查找子节点
          }));
    },
    buildTable(name,classid){
      let me=this;
      axios({
        url:'http://localhost:9090/studentInfo2',
        method:'GET',
        params:{
          name:name,
          classid:classid
        }
      }).then(res=>{
        console.log(res.data);
        me.tableData=res.data;
      })

    }

  }
}
</script>

<style>

</style>
