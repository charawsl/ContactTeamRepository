
<template>
    <div>
        <el-container>
            <el-header>赖哥和敏姐的通讯录</el-header>
            <el-main>

                    <!--搜索表单-->
                    <el-form :inline="true" :model="searchPeople" class="demo-form-inline">
                    <el-form-item label="姓名">
                        <el-input
                        v-model="searchPeople.name"
                        placeholder="请输入联系人姓名"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-select v-model="searchPeople.gender" placeholder="请选择">
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="2"></el-option>
                        </el-select>
                    </el-form-item>


                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                        <el-button type="info" @click="clear">清空</el-button>
                        <el-button type="warning" @click="favouratelist">收藏夹</el-button>
                        <el-button type="warning" @click="clear">返回首页</el-button>
                        <el-button type="warning" >
                            <a href="http://121.37.25.182:8080/download" download>导出</a>
                            
                        </el-button>

                        
                    </el-form-item>
                    <div>
                        <input type="file" @change="handleFileUpload" accept=".xlsx,.xls">
                        <button @click="uploadFile" :disabled="uploading">
                        {{ uploading ? '上传中...' : '上传' }}
                        </button>
                    </div>
                        
                    </el-form>
                        <br>
                    <!-- 添加人员按钮 -->
                     <el-row>
                        <el-button
                             style="float: center"
                            type="primary"
                             @click="dialogFormVisible = true; peopleNumber={id:'',number:''}; people = {id: '',name: '',phone: '',gender:'',address:''}"
                             >+ 添加联系人</el-button
                         >
                     </el-row>
                     <br>




                <el-table
                    :data="tableData"
                    height="620"
                    border
                    style="width: 100%">

                    
                    <el-table-column
                    prop="name"
                    label="姓名"
                    width="180">
                    </el-table-column>
                    <el-table-column
                    prop="phone"
                    label="主电话号码">

                    </el-table-column>
                    <el-table-column  label="号码操作" width="250">
                      
                        <template slot-scope="scope">
                            <el-button type="warning"  size = "mini" @click="peopleNumber={id:'',number:''};peopleNumber.id = scope.row.id; numberFormVisible = true;">添加号码 </el-button>

                                <el-popover
                            placement="right"
                            width="400"
                            trigger="click"
                            @show="showAllNumber(scope.row.id)">
                            <el-table :data="gridData">
                                <el-table-column width="150" property="number" label="其他号码"></el-table-column>
                                <el-table-column width="150" label="删除">
                                    <temp   late slot-scope="scope">
                                        <el-button type="danger" @click="deleteByNumber(scope.row.id); showAllNumber(scope.row.people_id)">删除</el-button>

                                    </temp>
                                </el-table-column>
                            </el-table>
                            <el-button slot="reference">展示其他号码</el-button>
                            </el-popover>
                    </template>
                    </el-table-column>

                    <el-table-column
                    label="性别" width="50">
                    <template slot-scope="scope">
                        <span>
                        {{scope.row.gender == "1" ? "男" : "女"}}</span>
                    </template>
                    </el-table-column>

                    <el-table-column
                    prop="address"
                    label="地址">
                    </el-table-column>
                    <el-table-column
                    prop="createTime"
                    label="创建时间">
                    </el-table-column>
                    <el-table-column
                    prop="updateTime"
                    label="更新时间">
                    </el-table-column>

                    <el-table-column
                        label="删除">
                        <template slot-scope="scope">
                            <el-button type="primary" @click="update(scope.row.id)">编辑</el-button>
                            <el-button type="danger" @click="deleteById(scope.row.id)">删除</el-button>

                        </template>
                            
                    </el-table-column>
                    <el-table-column
                        label="收藏">
                        <template slot-scope="scope">
                            <el-button type="warning" icon="el-icon-star-off" circle @click="favourate(scope.row.id)"></el-button>
                            <el-button type="warning" icon="el-icon-delete" circle @click="unfavourate(scope.row.id)"></el-button>
                        </template>
                            
                    </el-table-column>
                </el-table>
            </el-main>
        </el-container>



    <!--添加数据对话框表单-->
    <el-dialog ref="form" title="编辑员工" :visible.sync="dialogFormVisible" width="30%">
      <el-form ref="form" :model="people" label-width="80px" size="mini">
        <el-form-item label="姓名">
          <el-input v-model="people.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" >
          <el-select v-model="people.gender" placeholder="请选择" style="width:100%" >
             <el-option
              v-for="item in genderList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="主电话号码">
          <el-input v-model="people.phone"></el-input>
        </el-form-item>

        <el-form-item label="住址">
          <el-input v-model="people.address"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="add()">提交</el-button>
          <el-button @click="dialogFormVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!--添加数据对话框表单-->
    <el-dialog ref="form" title="添加号码" :visible.sync="numberFormVisible" width="30%">
      <el-form ref="form" :model="peopleNumber" label-width="80px" size="mini">

        <el-form-item label="电话号码">
          <el-input v-model="peopleNumber.number"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="addNumber()">提交</el-button>
          <el-button @click="numberFormVisible = false; peopleNumber = {id : '', number:''}">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <br>
    <!--分页工具条-->
    <div align = "center">
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :background="background"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount">
        
        </el-pagination>
    </div>
 </div>
</template>

<script>
import axios from 'axios';
export default{
    data () {
        return {
            //添加号码
            peopleNumber:{
                id:'',
                number:''
            },
            background: true,
            // 每页显示的条数
            pageSize: 5,
            // 总记录数
            totalCount: 100,
            // 当前页码
            currentPage: 1,
            //表单数据
            people: {
                id: '',
                name: '',
                phone: '',
                gender:'',
                address:''
            },
            gridData: [
            
            ],
            //表格数据
            tableData: [],
            genderList: [{id: 1,name: "男"},{id: 2,name: "女"}],
            //添加表格显示
            dialogFormVisible: false,
            numberFormVisible: false,
            searchPeople: {
                name: '',
                gender: '',
                favourite:''
            },
            selectedFile: null,
            uploading: false
        }
    },
    methods:{
        handleFileUpload(event) {
        this.selectedFile = event.target.files[0]
        },
        async uploadFile() {
        if (!this.selectedFile) {
            alert('请选择文件')
            return
        }

        // 文件类型验证
        const isExcel = this.selectedFile.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || 
                        this.selectedFile.type === 'application/vnd.ms-excel'
        if (!isExcel) {
            alert('只能上传Excel文件!')
            return
        }

        this.uploading = true
        const formData = new FormData()
        formData.append('file', this.selectedFile)

        try {
            const response = await axios.post('http://121.37.25.182:8080/import', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
            })
            console.log('上传成功', response.data)
            alert('上传成功')
            // 清空选择的文件
            this.selectedFile = null
            // 重置input
            event.target.value = ''
        } catch (error) {
            console.error('上传失败', error)
            alert('上传失败: ' + error.message)
        } finally {
            this.uploading = false
        }
        this.page();
        },

        //删除部门
        deleteById(id) {
            axios.delete("http://121.37.25.182:8080/listPeople/" + id)
            .then(() => {
                this.page();
            })

        },
        init(){
            axios.get("http://121.37.25.182:8080/listPeople")
            .then(resp=>{
            this.tableData=resp.data.data; //响应数据赋值给数据模型
        });
        },
        //添加数据
        add() {
            if(this.people.id){

                axios.post("http://121.37.25.182:8080/updatePeople", this.people)
                .then(() => {
                this.page();
                this.addNumber();
            })
            }
            else{


                axios.post("http://121.37.25.182:8080/listPeople", this.people)
                .then(() => {
                this.page();
            })
            }

        },
        page(){
            axios.get("http://121.37.25.182:8080/pageListPeople?page="+this.currentPage +"&pageSize="+this.pageSize+"&searchPeopleName="+this.searchPeople.name+"&searchPeopleGender="+this.searchPeople.gender+"&searchPeopleFavourite="+this.searchPeople.favourite)
            .then(resp=>{
            this.totalCount=resp.data.data.total; //响应数据赋值给数据模型
            this.tableData=resp.data.data.rows;
        });
        },
        handleSizeChange(val){
            this.pageSize = val;
            this.page();

        },
        handleCurrentChange(val){
            this.currentPage = val;
            this.page();

        },
        //查询方法
        onSubmit() {
            this.currentPage = 1;
            this.page();
        },
        //清空
        clear(){
            this.searchPeople = {name: "", gender: "", favourite: ""};
            this.page();
        },
        favouratelist(){
            this.currentPage = 1;
            this.searchPeople =  {name: "", gender: "", favourite: "1"};
            this.page();
        },
        //更新数据
        update(id) {
        //1. 打开窗口
        this.dialogFormVisible = true;
        //2. 发送请求

            //axios.get("http://localhost:8080/selectById?id="+id)
            axios.get("http://121.37.25.182:8080/selectById/"+id)
            .then((result) => {
                if (result.data.code == 1) {
                this.people = result.data.data;
                }
            });
        },
        //喜欢人
        favourate(id) {
        //发送请求
            axios.get("http://121.37.25.182:8080/favouritePeople/"+id)
                .then(() => {
                this.page();
            });
        },
        unfavourate(id) {
        //发送请求
            axios.get("http://121.37.25.182:8080/unfavouritePeople/"+id)
                .then(() => {
                this.page();
            });
        },
        excelexport(){
            axios.get("http://121.37.25.182:8080/download")
                .then(() => {
            });
        },
        addNumber(){
            axios.get("http://121.37.25.182:8080/addNumber/"+this.peopleNumber.id+"/"+this.peopleNumber.number)
            .then(() => {
            });
        },
        showAllNumber(id){
            axios.get("http://121.37.25.182:8080/selectNumberById/"+id)
            .then((result) => {
                if (result.data.code == 1) {
                this.gridData = result.data.data;
                
                }
            });
        },
        deleteByNumber(id){
            axios.get("http://121.37.25.182:8080/deleteByNumber/"+id)
            .then(() => {
            });
        },
        deleteByNumber1(id){
            axios.get("http://121.37.25.182:8080/deleteByNumber/"+id)
            .then(() => {
            });
        }


    },
    mounted(){
        this.page();
    }
}

</script>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    /* line-height: 850px; */
  }
  

  
</style>