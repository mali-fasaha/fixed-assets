import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';

@Component({
  selector: 'gha-fixed-asset-invoice-detail',
  templateUrl: './fixed-asset-invoice-detail.component.html'
})
export class FixedAssetInvoiceDetailComponent implements OnInit {
  fixedAssetInvoice: IFixedAssetInvoice;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fixedAssetInvoice }) => {
      this.fixedAssetInvoice = fixedAssetInvoice;
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
