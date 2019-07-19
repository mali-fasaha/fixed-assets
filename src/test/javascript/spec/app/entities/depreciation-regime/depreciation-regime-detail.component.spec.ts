/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { DepreciationRegimeDetailComponent } from 'app/entities/depreciation-regime/depreciation-regime-detail.component';
import { DepreciationRegime } from 'app/shared/model/depreciation-regime.model';

describe('Component Tests', () => {
  describe('DepreciationRegime Management Detail Component', () => {
    let comp: DepreciationRegimeDetailComponent;
    let fixture: ComponentFixture<DepreciationRegimeDetailComponent>;
    const route = ({ data: of({ depreciationRegime: new DepreciationRegime(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [DepreciationRegimeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DepreciationRegimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepreciationRegimeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.depreciationRegime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
