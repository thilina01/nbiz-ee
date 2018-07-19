/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SaleInvoiceDetailComponent } from 'app/entities/sale-invoice/sale-invoice-detail.component';
import { SaleInvoice } from 'app/shared/model/sale-invoice.model';

describe('Component Tests', () => {
    describe('SaleInvoice Management Detail Component', () => {
        let comp: SaleInvoiceDetailComponent;
        let fixture: ComponentFixture<SaleInvoiceDetailComponent>;
        const route = ({ data: of({ saleInvoice: new SaleInvoice(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SaleInvoiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SaleInvoiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaleInvoiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.saleInvoice).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
