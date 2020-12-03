<template>
  <div style="width:100%; height:100%; margin-top: 20px">
    <el-col style="width: 280px" :offset="1">
      <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"
                 :size="280"></el-avatar>

      <div>{{ this.$store.state.role.username }}</div>

      <div style="margin-top: 15px">
        <!--        <el-tag>hardworking</el-tag>-->
        <!--        <el-tag type="success">efficient</el-tag>-->
        <!--        <el-tag type="danger">earnest</el-tag>-->
        <el-tag v-for="tag in tagList" :key="tag.name" :type="tag.type">
          {{ tag.name }}
        </el-tag>
      </div>

      <div style="width: 280px; margin-top: 20px" align="left">
        {{ description }}
      </div>

      <div style="margin-top: 15px"><i class="el-icon-office-building"> SUSTech</i></div>
      <div style="margin-top: 15px"><i class="el-icon-location-outline"> Shenzhen</i></div>
      <div style="margin-top: 15px"><i class="el-icon-message"> 11111111@mail.sustech.edu.cn</i></div>
    </el-col>

    <el-col :span="15" :offset="1">
      <el-tabs :tab-position="tabPosition" type="card" style="height: 100%; margin-top: 20px" v-model="activeName">
        <el-tab-pane label="Overview" name="overview">
          <div style="margin-top: 10px">Recent project</div>
          <el-row>
            <el-col :span="12">
              <el-card class="box-card" style="width: 90%; margin-top: 20px" shadow="never">
                <div>Project 0</div>
                <div>Introduction</div>
                <div>CS102A</div>
              </el-card>
            </el-col>

            <el-col :span="12">
              <el-card class="box-card" style="width: 90%; margin-top: 20px" shadow="never">
                <div>Project 1</div>
                <div>Introduction</div>
                <div>CS202</div>
              </el-card>
            </el-col>

            <el-col :span="12">
              <el-card class="box-card" style="width: 90%; margin-top: 20px" shadow="never">
                <div>Project 2</div>
                <div>Introduction</div>
                <div>CS207</div>
              </el-card>
            </el-col>
          </el-row>
          <div style="margin-top: 40px">Skill List</div>

          <div style="margin-top: 15px">
            <el-tag>Java</el-tag>
            <el-tag type="success">cpp</el-tag>
            <el-tag type="danger">Spring Boot</el-tag>
            <el-tag>vue</el-tag>
            <el-tag type="success">UI Design</el-tag>
          </div>

          <div style="margin-top: 40px">Statistic</div>

        </el-tab-pane>

        <el-tab-pane label="History" name="history">
          <el-row>
            <el-col :span="14">
              <el-input v-model="nameFilter" placeholder="Find a project..."
                        style="width: 100%"></el-input>
            </el-col>

            <el-col :span="3" style="margin-left: 20px">
              <el-select v-model="valueYear" placeholder="Year: All">
                <el-option v-for="year in years" :key="year.value"
                           :label="year.label" :value="year.value"></el-option>
              </el-select>
            </el-col>

            <el-col :span="3" style="margin-left: 20px">
              <el-select v-model="valueCourse" placeholder="Course: All" style="width: 110%">
                <el-option v-for="course in courses" :key="course.value"
                           :label="course.label" :value="course.value"></el-option>
              </el-select>
            </el-col>
          </el-row>

          <el-card class="box-card" style="width: 90%; margin-top: 20px" shadow="never">
            <div>Project 0</div>
            <div>Introduction</div>
            <el-row>
              <div>CS102A</div>
              <div>Updated 2 years ago</div>
            </el-row>
          </el-card>

          <el-card class="box-card" style="width: 90%; margin-top: 20px" shadow="never">
            <div>Project 1</div>
            <div>Introduction</div>
            <el-row>
              <div>CS202</div>
              <div>Updated 1 years ago</div>
            </el-row>
          </el-card>

          <el-card class="box-card" style="width: 90%; margin-top: 20px" shadow="never">
            <div>Project 2</div>
            <div>Introduction</div>
            <el-row>
              <div>CS207</div>
              <div>Updated 1 years ago</div>
            </el-row>
          </el-card>

        </el-tab-pane>
      </el-tabs>
    </el-col>
  </div>
</template>

<script>
import {getUserHomeInfo} from '@/api/home_page'

export default {
  name: 'HomePage',
  components: {},
  data () {
    return {
      description: '',

      //tag
      tagList: [],

      //tab
      tabPosition: 'top',
      activeName: 'overview',

      //project history filter
      nameFilter: '',
      //year filter
      years: [
        {value: '0', label: '2018'},
        {value: '1', label: '2019'},
        {value: '2', label: '2020'},
        {value: '3', label: '2021'}
      ],
      valueYear: '',

      //course filter
      courses: [
        {value: '0', label: 'CS102A'},
        {value: '1', label: 'CS207'},
        {value: '2', label: 'CS202'},
        {value: '3', label: 'CS303'},
        {value: '4', label: 'CS307'},
        {value: '5', label: 'CS309'}],
      valueCourse: '',
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init home page')
      getUserHomeInfo().then(resp => {
          if (resp.data.code === 200) {
            let infoDict = resp.data.data

            // this.description = infoDict.description

            // let tagList = infoDict.tagList
            // for (let i = 0; i < tagList.length; ++i) {
            //   this.tagList.push({name: tagList[i].name,type: ''})
            // }
          } else if (resp.data.code === 400) {
            console.log(resp.data.message)
            this.$alert(resp.data.message, 'Tip', {
              confirmButtonText: 'OK'
            })
          }
        }
      ).catch(failResp => {
        this.$alert('Error: ' + failResp.message, 'Tips', {
          confirmButtonText: 'OK'
        })
      })
    }
    // getTags () {
    //   console.log('get personal tags')
    //   getUserTag().then(resp => {
    //     if(resp.data.code === 200){
    //       var arr = eval('${list}');
    //     }else if(resp.data.code === 400){
    //       console.log(resp.data.message)
    //       this.$alert(resp.data.message, 'Tip', {
    //         confirmButtonText: 'OK'
    //       })
    //     }
    //   }).catch(failResp=>{
    //     this.$alert('Error' + failResp.message, 'Tips', {
    //       confirmButtonText: 'OK'
    //     })
    //   })
    // }
  }
}
</script>

<style scoped>
/*project history card style*/
.text {
  font-size: 14px;
  height: 15px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 480px;
}
</style>


<!--    <el-row style="margin-top: 20px">-->
<!--      <el-col :span="6" :offset="1">-->
<!--        <div>-->
<!--          &lt;!&ndash;                <el-avatar :size="200"><div style="font-size: 80px; text-align:center; margin-top: -5px">n</div></el-avatar>&ndash;&gt;-->
<!--          <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg" :size="200"></el-avatar>-->
<!--        </div>-->
<!--      </el-col>-->

<!--      <el-col :span="16" :offset="1">-->
<!--        <div style="font-size: 80px; padding: 0; text-align:left; margin-top: 20px">-->
<!--          {{ this.$store.state.role.username }}-->
<!--        </div>-->
<!--        <div style="padding: 0; text-align:left; margin-top: 20px">-->
<!--          <el-tag>hardworking</el-tag>-->
<!--          <el-tag type="success">efficient</el-tag>-->
<!--          <el-tag type="danger">earnest</el-tag>-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->

<!--    <el-row style="margin-top: 40px">-->
<!--      <el-col :span="19" :offset="1">-->
<!--        <el-input type="textarea" :rows="5" placeholder="" v-model="description">-->
<!--        </el-input>-->
<!--      </el-col>-->
<!--    </el-row>-->
