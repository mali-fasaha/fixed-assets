import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFileType } from 'app/shared/model/file-type.model';

@Component({
  selector: 'gha-file-type-detail',
  templateUrl: './file-type-detail.component.html'
})
export class FileTypeDetailComponent implements OnInit {
  fileType: IFileType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileType }) => {
      this.fileType = fileType;
    });
  }

  previousState() {
    window.history.back();
  }
}
