/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FileTypeDetailComponent } from 'app/entities/file-type/file-type-detail.component';
import { FileType } from 'app/shared/model/file-type.model';

describe('Component Tests', () => {
  describe('FileType Management Detail Component', () => {
    let comp: FileTypeDetailComponent;
    let fixture: ComponentFixture<FileTypeDetailComponent>;
    const route = ({ data: of({ fileType: new FileType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FileTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FileTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fileType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
