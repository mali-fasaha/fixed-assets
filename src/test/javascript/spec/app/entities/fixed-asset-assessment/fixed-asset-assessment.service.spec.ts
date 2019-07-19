/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FixedAssetAssessmentService } from 'app/entities/fixed-asset-assessment/fixed-asset-assessment.service';
import { IFixedAssetAssessment, FixedAssetAssessment, AssetCondition } from 'app/shared/model/fixed-asset-assessment.model';

describe('Service Tests', () => {
  describe('FixedAssetAssessment Service', () => {
    let injector: TestBed;
    let service: FixedAssetAssessmentService;
    let httpMock: HttpTestingController;
    let elemDefault: IFixedAssetAssessment;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FixedAssetAssessmentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FixedAssetAssessment(
        0,
        'AAAAAAA',
        AssetCondition.EXCELLENT,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            assessmentDate: currentDate.format(DATE_FORMAT),
            nextAssessmentDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a FixedAssetAssessment', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            assessmentDate: currentDate.format(DATE_FORMAT),
            nextAssessmentDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            assessmentDate: currentDate,
            nextAssessmentDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new FixedAssetAssessment(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a FixedAssetAssessment', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            assetCondition: 'BBBBBB',
            assessmentDate: currentDate.format(DATE_FORMAT),
            assessmentRemarks: 'BBBBBB',
            nameOfAssessingStaff: 'BBBBBB',
            nameOfAssessmentContractor: 'BBBBBB',
            currentServiceOutletCode: 'BBBBBB',
            currentPhysicalAddress: 'BBBBBB',
            nextAssessmentDate: currentDate.format(DATE_FORMAT),
            nameOfUser: 'BBBBBB',
            fixedAssetPicture: 'BBBBBB',
            fixedAssetItemId: 1,
            estimatedValue: 1,
            estimatedUsefulMonths: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            assessmentDate: currentDate,
            nextAssessmentDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of FixedAssetAssessment', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            assetCondition: 'BBBBBB',
            assessmentDate: currentDate.format(DATE_FORMAT),
            assessmentRemarks: 'BBBBBB',
            nameOfAssessingStaff: 'BBBBBB',
            nameOfAssessmentContractor: 'BBBBBB',
            currentServiceOutletCode: 'BBBBBB',
            currentPhysicalAddress: 'BBBBBB',
            nextAssessmentDate: currentDate.format(DATE_FORMAT),
            nameOfUser: 'BBBBBB',
            fixedAssetPicture: 'BBBBBB',
            fixedAssetItemId: 1,
            estimatedValue: 1,
            estimatedUsefulMonths: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            assessmentDate: currentDate,
            nextAssessmentDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FixedAssetAssessment', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
