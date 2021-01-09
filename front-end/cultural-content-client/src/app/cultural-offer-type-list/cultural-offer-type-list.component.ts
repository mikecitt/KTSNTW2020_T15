import { Component, Input, OnInit, Output, OnChanges, ViewChild, AfterViewInit } from '@angular/core';
import { CulturalOfferType} from '../model/cultural-offer-type';
import {MatTableDataSource} from '@angular/material/table';
import { EventEmitter } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-cultural-offer-type-list',
  templateUrl: './cultural-offer-type-list.component.html',
  styleUrls: ['./cultural-offer-type-list.component.scss']
})
export class CulturalOfferTypeListComponent implements OnInit {

  @Input()
  offerTypes: CulturalOfferType[];

  @Output()
  onCreateTypeEvent = new EventEmitter<any>();
  @Output()
  onDeleteTypeEvent = new EventEmitter<any>();
  @Output()
  onUpdateTypeEvent = new EventEmitter<any>();

  @Output()
  onPageChangeEvent = new EventEmitter<any>();

  displayedColumns: string[] = ['name', 'options'];
  @ViewChild(MatPaginator) paginator: MatPaginator;

  dataSource: MatTableDataSource<CulturalOfferType>;

  constructor() { }

  ngAfterViewInit(){
    this.paginator.length = 10;
    this.dataSource.paginator = this.paginator;
  }

  ngOnChanges(): void{
    this.dataSource = new MatTableDataSource<CulturalOfferType>(this.offerTypes);
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<CulturalOfferType>(this.offerTypes);
  }

  onPageChanged(paginator: any){
    console.log(paginator);
    this.onPageChangeEvent.emit(paginator);
  }

  createType(): void{
    this.onCreateTypeEvent.emit("dldkls");
  }

  deleteType(element: any): void{
    this.onDeleteTypeEvent.emit(element.id);
  }

  updateType(element: any): void{
    this.onUpdateTypeEvent.emit(element);
  }

}
