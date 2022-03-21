import Vue from 'vue'
import VueRouter from 'vue-router'
import Canteen from '../views/canteen'
import Foodmanage from '../views/canteen/foodmanage'
import Foodcatemanage from '../views/canteen/foodcatemanage'
import Measeldishes from '../views/canteen/measeldishes'
import Meaeqmanage from '../views/canteen/meaeqmanage'
import Measelnutpack from '../views/canteen/measelnutpack'
import Measetquery from '../views/canteen/measetquery'
import Measetstrate from '../views/canteen/measetstrate'
import Measystset from '../views/canteen/measystset'
import Foodtrace from '../views/canteen/foodtrace'
import Mindmealarr from '../views/canteen/mindmealarr'
import Mindmydishes from '../views/canteen/mindmydishes'
import Mindnut from '../views/canteen/mindnut'
import Dishesassess from '../views/canteen/dishesassess'
import Dishesanalysequery from '../views/canteen/dishesanalysequery'
import Dishespricequery from '../views/canteen/dishespricequery'
import Dishesquery from '../views/canteen/dishesquery'
import Placeeat from '../views/canteen/placeeat'
import Rawplanquery from '../views/canteen/rawplanquery'
import Resdishesquery from '../views/canteen/resdishesquery'
import Purchaseorder from '../views/canteen/purchaseorder'
import Suppliermanagement from '../views/canteen/suppliermanagement'
import Businessdata from '../views/canteen/businessdata'
import Distributioncenter from '../views/canteen/distributioncenter'
import Onlinedishesmanagement from '../views/canteen/onlinedishesmanagement'
import Ordermanagement from '../views/canteen/ordermanagement'
import Productioncenter from '../views/canteen/productioncenter'
import Restaurantinformation from '../views/canteen/restaurantinformation'
import Cupboardmanagement from '../views/canteen/cupboardmanagement'
import Takefood from '../views/canteen/takefood'
import Packageservice from '../views/canteen/packageservice'
import Onlineordering from '../views/canteen/onlineordering'
import Foodindifferentplaces from '../views/canteen/foodindifferentplaces'
import Healthmanagement from '../views/canteen/healthmanagement'
import Mealsettlementdetails from '../views/canteen/mealsettlementdetails'
import Canteencoststatistics from '../views/canteen/canteencoststatistics'
import Onlineshopping from '../views/canteen/onlineshopping'
import Productmanagement from '../views/canteen/productmanagement'
import Stockmanagement from '../views/canteen/stockmanagement'
import StandingBook from '../views/canteen/standingBook'
import Shoppingorder from '../views/canteen/shoppingorder'
import Service from '../views/service'
import Equipment from '../views/service/equipment'
import Paper from '../views/service/paper'
import Stationery from '../views/service/stationery'
import Budget from '../views/service/budget'
import Meeting from '../views/service/meeting'
import Buyplan from '../views/service/buyplan'
import Stockquery from '../views/service/stockquery'
import Inbound from '../views/service/inbound'
import Outbound from '../views/service/outbound'
import Budgetsetting from '../views/service/budgetsetting'
import Budgetamendment from '../views/service/budgetamendment'
import Stockstatistics from '../views/service/stockstatistics'
import Property from '../views/property'
import EquipmentLedger from '../views/property/equipmentLedger'
import Spacemanagement from '../views/property/spacemanagement'
import Jobplacement from '../views/property/jobplacement'
import Districtmanagement from '../views/property/districtmanagement'
import Materialmanagement from '../views/property/materialmanagement'
import Rankplan from '../views/property/rankplan'
import Koriyasu from '../views/property/koriyasu'
import EquipmentInspection from '../views/property/equipmentInspection'
import Gongdan from '../views/property/gongdan'
import Recordsquery from '../views/property/recordsquery'
import Propertyrepair from '../views/property/propertyrepair'
import Dutyrecord from '../views/property/dutyrecord'
import Accidentrecords from '../views/property/accidentrecords'
import Slmanagement from '../views/property/slmanagement'
import Irrigationmanagement from '../views/property/irrigationmanagement'
import Cleaningplanmanagement from '../views/property/cleaningplanmanagement'
import Inspectionandanalysis from '../views/property/inspectionandanalysis'
import Inspectionandmaintenance from '../views/property/inspectionandmaintenance'
import Repairapply from '../views/property/repairapply'
import Repairauditing from '../views/property/repairauditing'
import Home from '../views/home'
// import Login from '../views/login'



Vue.use(VueRouter)
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
const routes = [
  // {
  //   path: '/',
  //   redirect: '/login'
  // },
  {
    path: '/',
    redirect: '/service'
  },
  // {
  //   path:'/home',component:Home
  // },
  
  // {
  //   path:'/login',component:Login
  // },
  {
    path: '/canteen',
    component: Canteen,
    redirect: '/canteen/foodcatemanage',
    children: [
      { path: '/canteen/foodmanage', name: 'foodmanage', component: Foodmanage },
      { path: '/canteen/foodcatemanage', name: 'foodcatemanage', component: Foodcatemanage },
      { path: '/canteen/measeldishes', name: 'measeldishes', component: Measeldishes },
      { path: '/canteen/meaeqmanage', name: 'meaeqmanage', component: Meaeqmanage },
      { path: '/canteen/measelnutpack', name: 'measelnutpack', component: Measelnutpack },
      { path: '/canteen/measetquery', name: 'measetquery', component: Measetquery },
      { path: '/canteen/measetstrate', name: 'measetstrate', component: Measetstrate },
      { path: '/canteen/measystset', name: 'measystset', component: Measystset },
      { path: '/canteen/foodtrace', name: 'foodtrace', component: Foodtrace },
      { path: '/canteen/mindmealarr', name: 'mindmealarr', component: Mindmealarr },
      { path: '/canteen/mindmydishes', name: 'mindmydishes', component: Mindmydishes },
      { path: '/canteen/mindnut', name: 'mindnut', component: Mindnut },
      { path: '/canteen/dishesassess', name: 'dishesassess', component: Dishesassess },
      { path: '/canteen/dishesanalysequery', name: 'dishesanalysequery', component: Dishesanalysequery },
      { path: '/canteen/dishespricequery', name: 'dishespricequery', component: Dishespricequery },
      { path: '/canteen/dishesquery', name: 'dishesquery', component: Dishesquery },
      { path: '/canteen/placeeat', name: 'placeeat', component: Placeeat },
      { path: '/canteen/rawplanquery', name: 'rawplanquery', component: Rawplanquery },
      { path: '/canteen/resdishesquery', name: 'resdishesquery', component: Resdishesquery },
      { path: '/canteen/purchaseorder', name: 'purchaseorder', component: Purchaseorder },
      { path: '/canteen/suppliermanagement', name: 'suppliermanagement', component: Suppliermanagement },
      { path: '/canteen/businessdata', name: 'businessdata', component: Businessdata },
      { path: '/canteen/distributioncenter', name: 'distributioncenter', component: Distributioncenter },
      { path: '/canteen/onlinedishesmanagement', name: 'onlinedishesmanagement', component: Onlinedishesmanagement },
      { path: '/canteen/ordermanagement', name: 'ordermanagement', component: Ordermanagement },
      { path: '/canteen/productioncenter', name: 'productioncenter', component: Productioncenter },
      { path: '/canteen/restaurantinformation', name: 'restaurantinformation', component: Restaurantinformation },
      { path: '/canteen/cupboardmanagement', name: 'cupboardmanagement', component: Cupboardmanagement },
      { path: '/canteen/takefood', name: 'takefood', component: Takefood },
      { path: '/canteen/packageservice', name: 'packageservice', component: Packageservice },
      { path: '/canteen/onlineordering', name: 'onlineordering', component: Onlineordering },
      { path: '/canteen/foodindifferentplaces', name: 'foodindifferentplaces', component: Foodindifferentplaces },
      { path: '/canteen/healthmanagement', name: 'healthmanagement', component: Healthmanagement },
      { path: '/canteen/mealsettlementdetails', name: 'mealsettlementdetails', component: Mealsettlementdetails },
      { path: '/canteen/canteencoststatistics', name: 'canteencoststatistics', component: Canteencoststatistics },
      { path: '/canteen/onlineshopping', name: 'onlineshopping', component: Onlineshopping },
      { path: '/canteen/productmanagement', name: 'productmanagement', component: Productmanagement },
      // { path: '/canteen/stockmanagement', name: 'stockmanagement', component: Stockmanagement },
      { path: '/canteen/standingBook', name: 'standingBook', component: StandingBook },
      { path: '/canteen/shoppingorder', name: 'shoppingorder', component: Shoppingorder },
    ]
  },
  {
    path: '/service',component: Service,redirect: '/service/equipment',
    children:[
      { path: '/service/equipment', name: 'equipment', component: Equipment },
      { path: '/service/paper', name: 'paper', component:Paper },
      { path: '/service/stationery', name: 'stationery', component: Stationery },
      { path: '/service/budget', name: 'budget', component: Budget },
      { path: '/service/meeting', name: 'meeting', component: Meeting },
      { path: '/service/buyplan', name: 'buyplan', component: Buyplan },
      { path: '/service/stockquery', name: 'stockquery', component: Stockquery },
      { path: '/service/inbound', name: 'inbound', component: Inbound },
      { path: '/service/outbound', name: 'outbound', component: Outbound },
      { path: '/service/budgetsetting', name: 'budgetsetting', component: Budgetsetting },
      { path: '/service/budgetamendment', name: 'budgetamendment', component: Budgetamendment },
      { path: '/service/stockstatistics', name: 'stockstatistics', component: Stockstatistics },
    ]

  },
  {
    path: '/property',component: Property,redirect: '/property/equipmentLedger',
    children:[
      { path: '/property/equipmentLedger', name: 'equipmentLedger', component: EquipmentLedger },
      { path: '/property/spacemanagement', name: 'spacemanagement', component: Spacemanagement },
      { path: '/property/jobplacement', name: 'jobplacement', component: Jobplacement },
      { path: '/property/districtmanagement', name: 'districtmanagement', component:Districtmanagement },
      { path: '/property/materialmanagement', name: 'materialmanagement', component: Materialmanagement },
      { path: '/property/rankplan', name: 'rankplan', component: Rankplan },
      { path: '/property/koriyasu', name: 'koriyasu', component: Koriyasu },
      { path: '/property/equipmentInspection', name: 'equipmentInspection', component: EquipmentInspection },
      { path: '/property/gongdan', name: 'gongdan', component: Gongdan },
      { path: '/property/recordsquery', name: 'recordsquery', component: Recordsquery },
      { path: '/property/propertyrepair', name: 'propertyrepair', component: Propertyrepair },
      { path: '/property/dutyrecord', name: 'dutyrecord', component: Dutyrecord },
      { path: '/property/accidentrecords', name: 'accidentrecords', component: Accidentrecords },
      { path: '/property/slmanagement', name: 'slmanagement', component: Slmanagement },
      { path: '/property/irrigationmanagement', name: 'irrigationmanagement', component: Irrigationmanagement },
      { path: '/property/cleaningplanmanagement', name: 'cleaningplanmanagement', component: Cleaningplanmanagement },
      { path: '/property/inspectionandanalysis', name: 'inspectionandanalysis', component: Inspectionandanalysis },
      { path: '/property/inspectionandmaintenance', name: 'inspectionandmaintenance', component: Inspectionandmaintenance },
      { path: '/property/repairapply', name: 'repairapply', component: Repairapply },
      { path: '/property/repairauditing', name: 'repairauditing', component: Repairauditing },

    ]

  }

]

const router = new VueRouter({
  routes
})

export default router
