/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetAssessmentDetailComponent } from 'app/entities/fixed-asset-assessment/fixed-asset-assessment-detail.component';
import { FixedAssetAssessment } from 'app/shared/model/fixed-asset-assessment.model';

describe('Component Tests', () => {
  describe('FixedAssetAssessment Management Detail Component', () => {
    let comp: FixedAssetAssessmentDetailComponent;
    let fixture: ComponentFixture<FixedAssetAssessmentDetailComponent>;
    const route = ({ data: of({ fixedAssetAssessment: new FixedAssetAssessment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetAssessmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FixedAssetAssessmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixedAssetAssessmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fixedAssetAssessment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
