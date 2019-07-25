import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AssetDisposal } from 'app/shared/model/asset-disposal.model';
import { AssetDisposalService } from './asset-disposal.service';
import { AssetDisposalComponent } from './asset-disposal.component';
import { AssetDisposalDetailComponent } from './asset-disposal-detail.component';
import { AssetDisposalUpdateComponent } from './asset-disposal-update.component';
import { AssetDisposalDeletePopupComponent } from './asset-disposal-delete-dialog.component';
import { IAssetDisposal } from 'app/shared/model/asset-disposal.model';

@Injectable({ providedIn: 'root' })
export class AssetDisposalResolve implements Resolve<IAssetDisposal> {
  constructor(private service: AssetDisposalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAssetDisposal> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AssetDisposal>) => response.ok),
        map((assetDisposal: HttpResponse<AssetDisposal>) => assetDisposal.body)
      );
    }
    return of(new AssetDisposal());
  }
}

export const assetDisposalRoute: Routes = [
  {
    path: '',
    component: AssetDisposalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AssetDisposals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AssetDisposalDetailComponent,
    resolve: {
      assetDisposal: AssetDisposalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetDisposals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AssetDisposalUpdateComponent,
    resolve: {
      assetDisposal: AssetDisposalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetDisposals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AssetDisposalUpdateComponent,
    resolve: {
      assetDisposal: AssetDisposalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetDisposals'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const assetDisposalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AssetDisposalDeletePopupComponent,
    resolve: {
      assetDisposal: AssetDisposalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetDisposals'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
