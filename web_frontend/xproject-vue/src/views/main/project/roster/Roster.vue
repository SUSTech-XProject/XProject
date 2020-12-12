<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Student Roster</span>
    </div>

    <div class="table-btm-group">
      <el-button type="danger" plain @click="deleteDialogVisible = true">Delete</el-button>
      <el-button type="primary" plain @click="importDialogVisible = true">Import</el-button>
    </div>

    <el-dialog
      title="Tip"
      :visible.sync="deleteDialogVisible"
      width="30%"
      @open="handleDialogOpen">

      <span v-if="dialogSltList.length">Delete selected {{dialogSltList.length}} student(s) ?</span>
      <span v-else>Please select students</span>

      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false" v-if="dialogSltList.length">Cancel</el-button>
        <el-button type="primary" @click="deleteDialogVisible = false; deleteStd()">OK</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="Import"
      :visible.sync="importDialogVisible"
      width="30%">

      <el-upload
        class="upload-demo"
        ref="excelUpload"
        drag
        :action="null"
        :auto-upload="false"
        :before-upload="uploadExcel"
        :multiple="false">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">Drag the file here, or<em> click to upload</em></div>
        <div class="el-upload__tip" slot="tip">Only excel files can be uploaded</div>
      </el-upload>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelExcel" >Cancel</el-button>
        <el-button type="primary" @click="uploadExcelCaller">Upload</el-button>
      </span>
    </el-dialog>

    <el-card>
      <el-table
        ref="stdTable"
        :data="stdList"
        empty-text="No Data Found"
        :default-sort = "{prop: 'index', order: 'increasing'}"
        @filter-change="handleFilterChange"
        style="width: 100%">
        <el-table-column type="selection"/>
        <el-table-column label="" type="index" width="50px" sortable/>
        <el-table-column label="Student Name" prop="stdName" sortable/>
        <el-table-column label="SID" prop="stdNo" sortable/>
        <el-table-column label="Account Username" prop="username" sortable/>
        <el-table-column label="Class" prop="stdClass" sortable column-key="stdClass"
                         :filters="classFList" :filter-method="classFMethod" :filter-multiple="false"/>
        <el-table-column label="Email" prop="email" sortable/>
        <el-table-column label="Operation">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page.current"
          :page-size="page.page"
          layout="total, sizes, prev, pager, next, jumper"
          :total="page.total">
        </el-pagination>
      </div>

    </el-card>

  </el-card>
</template>

<script>
  import {getGradeList} from "@/api/grade";
  import {getDatetimeStr} from "@/utils/parse-day-time";
  import {postImportFromExcel} from "@/api/std_manage";

  export default {
    name: "Roster",
    components:{
    },
    data() {
      return {
        deleteDialogVisible: false,
        importDialogVisible: false,
        dialogSltList: [],
        teamRadioModel: 'all',
        stdList: [
          {
            index: 4,
            stdName: 'Gang Li',
            stdNo: '11816501',
            username: 'sustech11816501',
            stdClass: '1865',
            email: '11816501@mail.sustech.edu.cn',
          },
          {
            index: 5,
            stdName: 'Wushuang Ye',
            stdNo: '11816502',
            username: 'sustech11816502',
            stdClass: '1865',
            email: '11816502@mail.sustech.edu.cn',
          },
          {
            index: 6,
            stdName: 'Xiaohong he',
            stdNo: '11816601',
            username: 'sustech11816601',
            stdClass: '1866',
            email: '11816601@mail.sustech.edu.cn',
          },
        ],
        classFList: [
          {text: "1865", value: "1865"},
          {text: "1866", value: "1866"},
        ],
        page: {
          current: 1,
          size: 100,
          total: 400,
        }
      }
    },
    mounted () {
      // this.initGradebook()
    },
    methods: {
      handleDialogOpen () {
        this.dialogSltList = this.$refs.stdTable.selection;
      },
      deleteStd () {
        let sltList = this.$refs.stdTable.selection
        console.log("delete sltList=%o", sltList)
      },
      uploadExcelCaller () {
        this.$refs.excelUpload.submit()
      },
      uploadExcel (file) {
        let formData = new window.FormData()
        formData.append('file', file)
        postImportFromExcel(formData).then(resp => {
          console.log('In uploadExcel: %o', resp)
        }).catch(failResp => {
          console.log('fail in uploadExcel: %o', failResp)
        })
        this.importDialogVisible = false
      },
      cancelExcel () {
        this.$refs.excelUpload.clearFiles()
        this.importDialogVisible = false
      },
      handleFilterChange (filters) {
        console.log("filter value: %o", filters)
      },
      handleEdit (index, row) {
        console.log(index, row);
      },
      classFMethod (value, row, column) {
        return value === row.stdClass;
      },
      initGradebook () {
        this.stdList.splice(0, this.stdList.length)   // remove all
        let projId = this.$store.state.proj.projId

        getGradeList(projId).then(resp => {
          if (resp.data.code !== 200) {
            this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
              confirmButtonText: 'OK'
            })
            return false
          }
          this.stdList.splice(0, this.stdList.length)   // remove all
          for (let i = 0; i < resp.data.data.length; i ++) {
            let record = resp.data.data[i]
            record['listIdx'] = i
            console.log(record)
            this.stdList.push(record)
          }
          console.log(this.stdList)
        }).catch(failResp => {
          console.log('fail in getGradeList. message=' + failResp.message)
        })
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      },
    }
  }
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  #base-card {
    margin: 15px 10px
  }
  #title-text {
    font-size: 20px;
  }

  .table-btm-group {
    margin: 10px 0 20px 0;
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }

  #teamRadio {
    margin-right: 10px;
  }

  .el-pagination {
    margin: 30px 0 10px 0;
    text-align: center;
  }

  #title-text {
    font-size: 20px;
  }

  .upload-demo {
    text-align: center;
  }
</style>
