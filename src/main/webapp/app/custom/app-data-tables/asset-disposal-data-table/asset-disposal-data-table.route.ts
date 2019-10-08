import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { AssetDisposal, IAssetDisposal } from 'app/shared/model/asset-disposal.model';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { AssetDisposalDetailComponent } from 'app/entities/asset-disposal/asset-disposal-detail.component';
import { AssetDisposalUpdateComponent } from 'app/entities/asset-disposal/asset-disposal-update.component';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs/index';
import { AssetDisposalDeletePopupComponent } from 'app/entities/asset-disposal';
import { AssetDisposalDataTableService } from 'app/custom/app-data-tables/asset-disposal-data-table/asset-disposal-data-table.service';
import { AssetDisposalDataTableComponent } from 'app/custom/app-data-tables/asset-disposal-data-table/asset-disposal-data-table.component';

@Injectable({ providedIn: 'root' })
export class AssetDisposalResolve implements Resolve<IAssetDisposal> {
  constructor(private service: AssetDisposalDataTableService) {}

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

export const assetDisposalDataTableRoute: Routes = [
  {
    path: 'asset-disposal',
    component: AssetDisposalDataTableComponent,
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

export const assetDisposalDataTablePopupRoute: Routes = [
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
