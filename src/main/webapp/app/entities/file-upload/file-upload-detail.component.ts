import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFileUpload } from 'app/shared/model/file-upload.model';

@Component({
  selector: 'gha-file-upload-detail',
  templateUrl: './file-upload-detail.component.html'
})
export class FileUploadDetailComponent implements OnInit {
  fileUpload: IFileUpload;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileUpload }) => {
      this.fileUpload = fileUpload;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
