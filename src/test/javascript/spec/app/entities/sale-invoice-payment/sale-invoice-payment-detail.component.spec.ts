/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SaleInvoicePaymentDetailComponent } from 'app/entities/sale-invoice-payment/sale-invoice-payment-detail.component';
import { SaleInvoicePayment } from 'app/shared/model/sale-invoice-payment.model';

describe('Component Tests', () => {
    describe('SaleInvoicePayment Management Detail Component', () => {
        let comp: SaleInvoicePaymentDetailComponent;
        let fixture: ComponentFixture<SaleInvoicePaymentDetailComponent>;
        const route = ({ data: of({ saleInvoicePayment: new SaleInvoicePayment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SaleInvoicePaymentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SaleInvoicePaymentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaleInvoicePaymentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.saleInvoicePayment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
