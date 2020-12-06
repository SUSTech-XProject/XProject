<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Gradebook</span>
    </div>

    <el-table
      :data="gradeList"
      empty-text="No Data Found"
      :default-sort = "{prop: 'index', order: 'increasing'}"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="Creator: ">
              <span>{{ props.row.tchName }}</span>
              <span style="margin-left: 10px">{{ props.row.email }}</span>
            </el-form-item>
            <el-form-item label="Type: ">
              <span>{{ props.row.type }}</span>
            </el-form-item>
            <el-form-item v-if="props.row.derivedStr"
                          label="Derived from: ">
              <span>{{ props.row.derivedStr }}</span>
            </el-form-item>
            <br>
            <el-form-item label="Comment: " v-if="props.row.comments">
              <span>{{ props.row.comments }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="" type="index" width="50px" sortable/>
      <el-table-column label="Name" prop="rcdName" sortable/>
      <el-table-column label="Modified time" prop="modifiedTime" :formatter="dateTimeFormatter" sortable :sort-method="gradeSorter"/>
      <el-table-column label="Grade" sortable :sort-method="gradeSorter">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.content }}</span>
          <span v-if="scope.row.baseContent"
                style="margin-left: 2px">/ {{ scope.row.baseContent }}</span>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import {getGradeList} from "@/api/grade";
import {getDatetimeStr} from "@/utils/parse-day-time";

export default {
  name: "Gradebook",
  components:{
  },
  data() {
    return {
      gradeList: [
        {
          index: 1,
          name: 'assignment1',
          createdTime: '12/01/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Point',
          content: 96,
          baseContent: 100,
          derived: '',
          comment: '1.2 no pic -2; 1.4 no text -2'
        },
        {
          index: 2,
          name: 'assignment2',
          createdTime: '12/02/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Point',
          content: 90,
          baseContent: 100,
          derived: '',
          comment: 'None'
        },
        {
          index: 3,
          name: 'assignment3',
          createdTime: '12/03/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Point',
          content: 87,
          baseContent: 100,
          derived: '',
          comment: 'None'
        },
        {
          index: 4,
          name: 'report',
          createdTime: '12/06/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Grade',
          content: 'A',
          baseContent: null,
          derived: '',
          comment: 'report grade'
        },
        {
          index: 5,
          name: 'assignment total',
          createdTime: '12/08/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Point',
          content: 91.0,
          baseContent: 100,
          derived: 'assignment1 assignment2 assignment3',
          comment: 'assignment avg score'
        },
      ]
    }
  },
  mounted () {
    this.initGradebook()
  },
  methods:{
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.modifiedTime)
    },
    gradeSorter (row, col) {
      return row.content
    },
    getTime(str) {
      return 'abc'
    },
    initGradebook () {
      this.gradeList.splice(0, this.gradeList.length)   // remove all
      let projId = this.$store.state.proj.projId

      getGradeList(projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.gradeList.splice(0, this.gradeList.length)   // remove all
        for (let i = 0; i < resp.data.data.length; i ++) {
          let record = resp.data.data[i]
          record['listIdx'] = i
          console.log(record)
          this.gradeList.push(record)
        }
        console.log(this.gradeList)
      }).catch(failResp => {
        console.log('fail in getGradeList. message=' + failResp.message)
      })
    }
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
</style>
