import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MessageToken } from 'app/shared/model/message-token.model';
import { MessageTokenService } from './message-token.service';
import { MessageTokenComponent } from './message-token.component';
import { MessageTokenDetailComponent } from './message-token-detail.component';
import { MessageTokenUpdateComponent } from './message-token-update.component';
import { MessageTokenDeletePopupComponent } from './message-token-delete-dialog.component';
import { IMessageToken } from 'app/shared/model/message-token.model';

@Injectable({ providedIn: 'root' })
export class MessageTokenResolve implements Resolve<IMessageToken> {
  constructor(private service: MessageTokenService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMessageToken> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MessageToken>) => response.ok),
        map((messageToken: HttpResponse<MessageToken>) => messageToken.body)
      );
    }
    return of(new MessageToken());
  }
}

export const messageTokenRoute: Routes = [
  {
    path: '',
    component: MessageTokenComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'MessageTokens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MessageTokenDetailComponent,
    resolve: {
      messageToken: MessageTokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageTokens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MessageTokenUpdateComponent,
    resolve: {
      messageToken: MessageTokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageTokens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MessageTokenUpdateComponent,
    resolve: {
      messageToken: MessageTokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageTokens'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const messageTokenPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MessageTokenDeletePopupComponent,
    resolve: {
      messageToken: MessageTokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MessageTokens'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
