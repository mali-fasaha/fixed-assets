import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFixedAssetAssessment } from 'app/shared/model/fixed-asset-assessment.model';

@Component({
  selector: 'gha-fixed-asset-assessment-detail',
  templateUrl: './fixed-asset-assessment-detail.component.html'
})
export class FixedAssetAssessmentDetailComponent implements OnInit {
  fixedAssetAssessment: IFixedAssetAssessment;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fixedAssetAssessment }) => {
      this.fixedAssetAssessment = fixedAssetAssessment;
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
