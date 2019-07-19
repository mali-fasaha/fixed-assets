import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFixedAssetAssessment } from 'app/shared/model/fixed-asset-assessment.model';

type EntityResponseType = HttpResponse<IFixedAssetAssessment>;
type EntityArrayResponseType = HttpResponse<IFixedAssetAssessment[]>;

@Injectable({ providedIn: 'root' })
export class FixedAssetAssessmentService {
  public resourceUrl = SERVER_API_URL + 'api/fixed-asset-assessments';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/fixed-asset-assessments';

  constructor(protected http: HttpClient) {}

  create(fixedAssetAssessment: IFixedAssetAssessment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fixedAssetAssessment);
    return this.http
      .post<IFixedAssetAssessment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fixedAssetAssessment: IFixedAssetAssessment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fixedAssetAssessment);
    return this.http
      .put<IFixedAssetAssessment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFixedAssetAssessment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFixedAssetAssessment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFixedAssetAssessment[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(fixedAssetAssessment: IFixedAssetAssessment): IFixedAssetAssessment {
    const copy: IFixedAssetAssessment = Object.assign({}, fixedAssetAssessment, {
      assessmentDate:
        fixedAssetAssessment.assessmentDate != null && fixedAssetAssessment.assessmentDate.isValid()
          ? fixedAssetAssessment.assessmentDate.format(DATE_FORMAT)
          : null,
      nextAssessmentDate:
        fixedAssetAssessment.nextAssessmentDate != null && fixedAssetAssessment.nextAssessmentDate.isValid()
          ? fixedAssetAssessment.nextAssessmentDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.assessmentDate = res.body.assessmentDate != null ? moment(res.body.assessmentDate) : null;
      res.body.nextAssessmentDate = res.body.nextAssessmentDate != null ? moment(res.body.nextAssessmentDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fixedAssetAssessment: IFixedAssetAssessment) => {
        fixedAssetAssessment.assessmentDate =
          fixedAssetAssessment.assessmentDate != null ? moment(fixedAssetAssessment.assessmentDate) : null;
        fixedAssetAssessment.nextAssessmentDate =
          fixedAssetAssessment.nextAssessmentDate != null ? moment(fixedAssetAssessment.nextAssessmentDate) : null;
      });
    }
    return res;
  }
}
