import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepreciationRegime } from 'app/shared/model/depreciation-regime.model';
import { DepreciationRegimeService } from './depreciation-regime.service';
import { DepreciationRegimeComponent } from './depreciation-regime.component';
import { DepreciationRegimeDetailComponent } from './depreciation-regime-detail.component';
import { DepreciationRegimeUpdateComponent } from './depreciation-regime-update.component';
import { DepreciationRegimeDeletePopupComponent } from './depreciation-regime-delete-dialog.component';
import { IDepreciationRegime } from 'app/shared/model/depreciation-regime.model';

@Injectable({ providedIn: 'root' })
export class DepreciationRegimeResolve implements Resolve<IDepreciationRegime> {
  constructor(private service: DepreciationRegimeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepreciationRegime> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DepreciationRegime>) => response.ok),
        map((depreciationRegime: HttpResponse<DepreciationRegime>) => depreciationRegime.body)
      );
    }
    return of(new DepreciationRegime());
  }
}

export const depreciationRegimeRoute: Routes = [
  {
    path: '',
    component: DepreciationRegimeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'DepreciationRegimes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepreciationRegimeDetailComponent,
    resolve: {
      depreciationRegime: DepreciationRegimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepreciationRegimes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepreciationRegimeUpdateComponent,
    resolve: {
      depreciationRegime: DepreciationRegimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepreciationRegimes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepreciationRegimeUpdateComponent,
    resolve: {
      depreciationRegime: DepreciationRegimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepreciationRegimes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const depreciationRegimePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DepreciationRegimeDeletePopupComponent,
    resolve: {
      depreciationRegime: DepreciationRegimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DepreciationRegimes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
