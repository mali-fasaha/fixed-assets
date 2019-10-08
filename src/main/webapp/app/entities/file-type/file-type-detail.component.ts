import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFileType } from 'app/shared/model/file-type.model';

@Component({
  selector: 'gha-file-type-detail',
  templateUrl: './file-type-detail.component.html'
})
export class FileTypeDetailComponent implements OnInit {
  fileType: IFileType;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileType }) => {
      this.fileType = fileType;
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
