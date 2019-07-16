import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AssetTransaction } from 'app/shared/model/asset-transaction.model';
import { AssetTransactionService } from './asset-transaction.service';
import { AssetTransactionComponent } from './asset-transaction.component';
import { AssetTransactionDetailComponent } from './asset-transaction-detail.component';
import { AssetTransactionUpdateComponent } from './asset-transaction-update.component';
import { AssetTransactionDeletePopupComponent } from './asset-transaction-delete-dialog.component';
import { IAssetTransaction } from 'app/shared/model/asset-transaction.model';

@Injectable({ providedIn: 'root' })
export class AssetTransactionResolve implements Resolve<IAssetTransaction> {
  constructor(private service: AssetTransactionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAssetTransaction> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AssetTransaction>) => response.ok),
        map((assetTransaction: HttpResponse<AssetTransaction>) => assetTransaction.body)
      );
    }
    return of(new AssetTransaction());
  }
}

export const assetTransactionRoute: Routes = [
  {
    path: '',
    component: AssetTransactionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AssetTransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AssetTransactionDetailComponent,
    resolve: {
      assetTransaction: AssetTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetTransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AssetTransactionUpdateComponent,
    resolve: {
      assetTransaction: AssetTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetTransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AssetTransactionUpdateComponent,
    resolve: {
      assetTransaction: AssetTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetTransactions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const assetTransactionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AssetTransactionDeletePopupComponent,
    resolve: {
      assetTransaction: AssetTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetTransactions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
