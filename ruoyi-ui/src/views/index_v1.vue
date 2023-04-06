<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>



    
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import { getMainData } from '@/api/music/main'
// const lineChartData = {
//   newVisitis: {
//     expectedData: [4, 3, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
//   }
// }

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart
  },
  data() {
    return {
      // lineChartData: lineChartData.newVisitis
      lineChartData: []
    }
  },
  created() {
    this.getData();
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    getData() {
      getMainData().then(res => {
        console.log(res.months)

          var lineChartData = {
          newVisitis: {
            expectedData: res.months,
          }
          }

        this.lineChartData = lineChartData.newVisitis
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
