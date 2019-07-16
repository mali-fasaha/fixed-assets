import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IScannedDocument } from 'app/shared/model/scanned-document.model';

@Component({
  selector: 'jhi-scanned-document-detail',
  templateUrl: './scanned-document-detail.component.html'
})
export class ScannedDocumentDetailComponent implements OnInit {
  scannedDocument: IScannedDocument;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ scannedDocument }) => {
      this.scannedDocument = scannedDocument;
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
