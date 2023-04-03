<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="歌名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入歌名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="artists">
        <el-input
          v-model="queryParams.artists"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者id" prop="artistsId">
        <el-input
          v-model="queryParams.artistsId"
          placeholder="请输入作者id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布时间">
        <el-date-picker
          v-model="daterangePublicTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['music:music:add']"
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
          v-hasPermi="['music:music:edit']"
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
          v-hasPermi="['music:music:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:music:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="musicList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="歌曲id" align="center" prop="musicId" />
      <el-table-column label="歌名" align="center" prop="name" />
      <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="封面" align="center" prop="coverImg" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverImg" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="作者" align="center" prop="artists" />
      <el-table-column label="作者id" align="center" prop="artistsId" />
      <el-table-column label="发布时间" align="center" prop="publicTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publicTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['music:music:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:music:remove']"
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

    <!-- 添加或修改歌曲信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="歌名" prop="name">
          <el-input v-model="form.name" placeholder="请输入歌名" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="封面" prop="coverImg">
          <image-upload v-model="form.coverImg"/>
        </el-form-item>
        <el-form-item label="作者" prop="artists">
          <el-input v-model="form.artists" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="作者id" prop="artistsId">
          <el-input v-model="form.artistsId" placeholder="请输入作者id" />
        </el-form-item>
        <el-form-item label="发布时间" prop="publicTime">
          <el-date-picker clearable
            v-model="form.publicTime"
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
import { listMusic, getMusic, delMusic, addMusic, updateMusic } from "@/api/music/music";

export default {
  name: "Music",
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
      // 歌曲信息表格数据
      musicList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 发布时间时间范围
      daterangePublicTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        description: null,
        artists: null,
        artistsId: null,
        publicTime: null
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
    /** 查询歌曲信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangePublicTime && '' != this.daterangePublicTime) {
        this.queryParams.params["beginPublicTime"] = this.daterangePublicTime[0];
        this.queryParams.params["endPublicTime"] = this.daterangePublicTime[1];
      }
      listMusic(this.queryParams).then(response => {
        this.musicList = response.rows;
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
        musicId: null,
        name: null,
        description: null,
        coverImg: null,
        artists: null,
        artistsId: null,
        publicTime: null
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
      this.daterangePublicTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.musicId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加歌曲信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const musicId = row.musicId || this.ids
      getMusic(musicId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改歌曲信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.musicId != null) {
            updateMusic(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMusic(this.form).then(response => {
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
      const musicIds = row.musicId || this.ids;
      this.$modal.confirm('是否确认删除歌曲信息编号为"' + musicIds + '"的数据项？').then(function() {
        return delMusic(musicIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/music/export', {
        ...this.queryParams
      }, `music_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
