<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者id" prop="artistId">
        <el-input
          v-model="queryParams.artistId"
          placeholder="请输入作者id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者姓名" prop="artistName">
        <el-input
          v-model="queryParams.artistName"
          placeholder="请输入作者姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="播放数" prop="playCount">
        <el-input
          v-model="queryParams.playCount"
          placeholder="请输入播放数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="转发数" prop="shareCount">
        <el-input
          v-model="queryParams.shareCount"
          placeholder="请输入转发数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布时间" prop="publishTime">
        <el-date-picker clearable
          v-model="queryParams.publishTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发布时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['music:mv:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['music:mv:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['music:mv:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:mv:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mvList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Mvid" align="center" prop="mvId" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="作者id" align="center" prop="artistId" />
      <el-table-column label="作者姓名" align="center" prop="artistName" />
      <el-table-column label="分辨率" align="center" prop="brs" />
      <el-table-column label="描述" align="center" prop="description" >
        <template slot-scope="scope">
        <div class="table-cell-container">
          <div class="table-cell-content">{{ scope.row.description }}</div>
        </div>
      </template>  
      </el-table-column>
      <el-table-column label="时长" align="center" prop="duration" />
      <el-table-column label="播放数" align="center" prop="playCount" />
      <el-table-column label="转发数" align="center" prop="shareCount" />
      <el-table-column label="发布时间" align="center" prop="publishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['music:mv:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:mv:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改Mv信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="作者id" prop="artistId">
          <el-input v-model="form.artistId" placeholder="请输入作者id" />
        </el-form-item>
        <el-form-item label="作者姓名" prop="artistName">
          <el-input v-model="form.artistName" placeholder="请输入作者姓名" />
        </el-form-item>
        <el-form-item label="分辨率" prop="brs">
          <el-input v-model="form.brs" placeholder="请输入分辨率" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.desc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="时长" prop="duration">
          <el-input v-model="form.duration" placeholder="请输入时长" />
        </el-form-item>
        <el-form-item label="播放数" prop="playCount">
          <el-input v-model="form.playCount" placeholder="请输入播放数" />
        </el-form-item>
        <el-form-item label="转发数" prop="shareCount">
          <el-input v-model="form.shareCount" placeholder="请输入转发数" />
        </el-form-item>
        <el-form-item label="发布时间" prop="publishTime">
          <el-date-picker clearable
            v-model="form.publishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMv, getMv, delMv, addMv, updateMv } from "@/api/music/mv";

export default {
  name: "Mv",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // Mv信息表格数据
      mvList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        artistId: null,
        artistName: null,
        description: null,
        playCount: null,
        shareCount: null,
        publishTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询Mv信息列表 */
    getList() {
      this.loading = true;
      listMv(this.queryParams).then(response => {
        this.mvList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        mvId: null,
        name: null,
        artistId: null,
        artistName: null,
        brs: null,
        description: null,
        duration: null,
        playCount: null,
        shareCount: null,
        publishTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.mvId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加Mv信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const mvId = row.mvId || this.ids
      getMv(mvId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改Mv信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.mvId != null) {
            updateMv(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMv(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const mvIds = row.mvId || this.ids;
      this.$modal.confirm('是否确认删除Mv信息编号为"' + mvIds + '"的数据项？').then(function() {
        return delMv(mvIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/mv/export', {
        ...this.queryParams
      }, `mv_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.table-cell-container {
  height: 100px;
  overflow: auto;
}
.table-cell-content {
  white-space: pre-wrap;
}
</style>