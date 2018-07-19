/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SupplierDetailComponent } from 'app/entities/supplier/supplier-detail.component';
import { Supplier } from 'app/shared/model/supplier.model';

describe('Component Tests', () => {
    describe('Supplier Management Detail Component', () => {
        let comp: SupplierDetailComponent;
        let fixture: ComponentFixture<SupplierDetailComponent>;
        const route = ({ data: of({ supplier: new Supplier(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SupplierDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplierDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplierDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplier).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
