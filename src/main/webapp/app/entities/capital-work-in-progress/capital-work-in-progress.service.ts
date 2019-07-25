import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';

type EntityResponseType = HttpResponse<ICapitalWorkInProgress>;
type EntityArrayResponseType = HttpResponse<ICapitalWorkInProgress[]>;

@Injectable({ providedIn: 'root' })
export class CapitalWorkInProgressService {
  public resourceUrl = SERVER_API_URL + 'api/capital-work-in-progresses';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/capital-work-in-progresses';

  constructor(protected http: HttpClient) {}

  create(capitalWorkInProgress: ICapitalWorkInProgress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(capitalWorkInProgress);
    return this.http
      .post<ICapitalWorkInProgress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(capitalWorkInProgress: ICapitalWorkInProgress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(capitalWorkInProgress);
    return this.http
      .put<ICapitalWorkInProgress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICapitalWorkInProgress>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICapitalWorkInProgress[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICapitalWorkInProgress[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(capitalWorkInProgress: ICapitalWorkInProgress): ICapitalWorkInProgress {
    const copy: ICapitalWorkInProgress = Object.assign({}, capitalWorkInProgress, {
      transactionMonth:
        capitalWorkInProgress.transactionMonth != null && capitalWorkInProgress.transactionMonth.isValid()
          ? capitalWorkInProgress.transactionMonth.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transactionMonth = res.body.transactionMonth != null ? moment(res.body.transactionMonth) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((capitalWorkInProgress: ICapitalWorkInProgress) => {
        capitalWorkInProgress.transactionMonth =
          capitalWorkInProgress.transactionMonth != null ? moment(capitalWorkInProgress.transactionMonth) : null;
      });
    }
    return res;
  }
}
