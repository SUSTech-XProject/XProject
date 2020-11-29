<template>
  <div id="table" class="table">
    <el-table
      :data="tableData"
      style="width: 100%"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="date"
        label="Date"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="Name"
        width="180">
      </el-table-column>
      <el-table-column
        prop="event"
        label="Event">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 3 === 0) {
        return 'warning-row'
      } else if (rowIndex % 3 === 1) {
        // alert(row.name)
        return 'normal-row'
      } else {
        return ''
      }
    }
  },
  data () {
    return {
      tableData: [{
        date: '2020-11-11',
        name: 'Student1',
        event: 'Update project file'
      }, {
        date: '2020-11-13',
        name: 'Teacher1',
        event: 'Resubmit project file'
      }, {
        date: '2020-11-15',
        name: 'Student2',
        event: 'Quit your team'
      }, {
        date: '2020-11-15',
        name: 'Student3',
        evnet: 'Join your team'
      }]
    }
  }
}
</script>

<style scoped>
.el-table .warning-row {
  background: #fdf5e6;
}

.el-table .normal-row {
  background: #f0f9eb;
}
/*.table {*/
/*  position: absolute;*/

/*}*/
</style>
