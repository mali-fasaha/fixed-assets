/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetItemDetailComponent } from 'app/entities/fixed-asset-item/fixed-asset-item-detail.component';
import { FixedAssetItem } from 'app/shared/model/fixed-asset-item.model';

describe('Component Tests', () => {
  describe('FixedAssetItem Management Detail Component', () => {
    let comp: FixedAssetItemDetailComponent;
    let fixture: ComponentFixture<FixedAssetItemDetailComponent>;
    const route = ({ data: of({ fixedAssetItem: new FixedAssetItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FixedAssetItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixedAssetItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fixedAssetItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
