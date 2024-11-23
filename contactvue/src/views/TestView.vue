<template>
    <div>
        <el-container>
            <el-header>赖哥的通讯录</el-header>
            <el-main>
                           <!-- 添加表单 -->
                    <el-form :inline="true" :model="formInline" class="demo-form-inline" >
                        <el-form-item label = "姓名">
                            <el-input v-model="formInline.user" placeholder="姓名"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-input v-model="formInline.user" placeholder="手机号码"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">添加</el-button>
                        </el-form-item>
                    </el-form>

                    
                <el-table
                    :data="tableData"
                    height="650"
                    border
                    style="width: 100%">
                    <el-table-column
                    prop="image"
                    label="头像"
                    width="180">
                    </el-table-column>
                    <el-table-column
                    prop="name"
                    label="姓名"
                    width="180">
                    </el-table-column>
                    <el-table-column
                    prop="number"
                    label="电话号码">
                    </el-table-column>
                    <el-table-column
                    prop="gender"
                    label="性别">
                    </el-table-column>
                    <el-table-column
                    prop="address"
                    label="地址">
                    </el-table-column>
                    <el-table-column
                    label="删除">
                    <el-button type="danger">删除</el-button>
                    </el-table-column>
                </el-table>
                </el-main>
        </el-container>
 </div>
</template>

<script>
export default{
    data () {
        return {
            formInline: {
                user: '',
                region: ''
            }

        }
    },
    methods:{
        onSubmit() {
            console.log('submit!');
        }
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
  }
</style>