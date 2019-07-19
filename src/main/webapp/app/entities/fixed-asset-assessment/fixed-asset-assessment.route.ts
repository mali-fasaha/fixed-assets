import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FixedAssetAssessment } from 'app/shared/model/fixed-asset-assessment.model';
import { FixedAssetAssessmentService } from './fixed-asset-assessment.service';
import { FixedAssetAssessmentComponent } from './fixed-asset-assessment.component';
import { FixedAssetAssessmentDetailComponent } from './fixed-asset-assessment-detail.component';
import { FixedAssetAssessmentUpdateComponent } from './fixed-asset-assessment-update.component';
import { FixedAssetAssessmentDeletePopupComponent } from './fixed-asset-assessment-delete-dialog.component';
import { IFixedAssetAssessment } from 'app/shared/model/fixed-asset-assessment.model';

@Injectable({ providedIn: 'root' })
export class FixedAssetAssessmentResolve implements Resolve<IFixedAssetAssessment> {
  constructor(private service: FixedAssetAssessmentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFixedAssetAssessment> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FixedAssetAssessment>) => response.ok),
        map((fixedAssetAssessment: HttpResponse<FixedAssetAssessment>) => fixedAssetAssessment.body)
      );
    }
    return of(new FixedAssetAssessment());
  }
}

export const fixedAssetAssessmentRoute: Routes = [
  {
    path: '',
    component: FixedAssetAssessmentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'FixedAssetAssessments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FixedAssetAssessmentDetailComponent,
    resolve: {
      fixedAssetAssessment: FixedAssetAssessmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetAssessments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FixedAssetAssessmentUpdateComponent,
    resolve: {
      fixedAssetAssessment: FixedAssetAssessmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetAssessments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FixedAssetAssessmentUpdateComponent,
    resolve: {
      fixedAssetAssessment: FixedAssetAssessmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetAssessments'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fixedAssetAssessmentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FixedAssetAssessmentDeletePopupComponent,
    resolve: {
      fixedAssetAssessment: FixedAssetAssessmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetAssessments'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
